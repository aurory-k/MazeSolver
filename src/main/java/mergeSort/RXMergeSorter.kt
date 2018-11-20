package mergeSort

import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

object RXMergeSorter {

    fun sort(list: List<Int>): Single<List<Int>> {
        //println("Sorting from thread ${Thread.currentThread().name}")
        if (list.size <= 1) {
            return Single.just(list)
        }

        val (leftList, rightList) = splitList(list)

        return Flowable.fromArray(leftList, rightList)
                .flatMapSingle { sort(it).subscribeOn(Schedulers.computation()) }
                .toList()
                .map { it[0] to it[1] }
                .map { (left, right) -> merge(left, right) }

    }

    private fun splitList(list: List<Int>): Pair<List<Int>, List<Int>> {
        val middle = list.size / 2
        val leftList = list.subList(0, middle)
        val rightList = list.subList(middle, list.size)

        return Pair(leftList, rightList)
    }

    private fun merge(leftList: List<Int>, rightList: List<Int>): List<Int> {

        var returnedList = listOf<Int>()
        var leftIndex = 0
        var rightIndex = 0

        while (leftIndex < leftList.size && rightIndex < rightList.size) {
            if (leftList[leftIndex] <= rightList[rightIndex]) {
                returnedList = returnedList.plus(leftList[leftIndex])
                leftIndex++
            } else if (rightList[rightIndex] <= leftList[leftIndex]) {
                returnedList = returnedList.plus(rightList[rightIndex])
                rightIndex++
            }

        }

        while (leftIndex < leftList.size) {
            returnedList = returnedList.plus(leftList[leftIndex++])
        }

        while (rightIndex < rightList.size) {
            returnedList = returnedList.plus(rightList[rightIndex++])
        }

        return returnedList
    }

}