package mergeSort

class NormalMergeSorter {

    fun sort(list: List<Int>): List<Int> {
        if (list.size <= 1) {
            return list
        }

        val (leftList, rightList) = splitList(list)

        return merge(sort(leftList), sort(rightList))
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