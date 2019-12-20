package com.nenaner.aoc2019.day8

import com.nenaner.aoc2019.FileManager
import com.nenaner.aoc2019.OutputLogger
import org.springframework.stereotype.Component

@Component
class SpaceImageProcessor(private var outputLogger: OutputLogger,
                          private var fileManager: FileManager) {
    fun findLayerWithTheFewestZeroesFromFile() {
        val imageHeight = 6
        val imageWidth = 25
        val spaceImageData = fileManager.readFileAsLinesUsingGetResourceAsStream(fileNameContainingSpaceImageData).toMutableList()[0]

        val spaceImageLayers = processSpaceImageData(spaceImageData, imageHeight, imageWidth)

        val zeroAsByte: Byte = 0
        var layerWithTheFewestZeroes: SpaceImageLayer = spaceImageLayers[0]
        var countOfFewestZeroes = layerWithTheFewestZeroes.countOfValue(zeroAsByte)
        for (spaceImageLayer in spaceImageLayers) {
            if (countOfFewestZeroes > spaceImageLayer.countOfValue(zeroAsByte)) {
                layerWithTheFewestZeroes = spaceImageLayer
                countOfFewestZeroes = layerWithTheFewestZeroes.countOfValue(zeroAsByte)
            }
        }

        outputLogger.info("image with the fewest zeroes: ${System.lineSeparator()}${layerWithTheFewestZeroes.getPrintableImage()}")
        val oneAsByte: Byte = 1
        val twoAsByte: Byte = 2
        val countOfOnes = layerWithTheFewestZeroes.countOfValue(oneAsByte)
        val countOfTwos = layerWithTheFewestZeroes.countOfValue(twoAsByte)
        outputLogger.info("count of 1's: $countOfOnes, count of 2's: $countOfTwos")
        outputLogger.info("answer: ${countOfOnes * countOfTwos}")
    }

    private fun processSpaceImageData(spaceImageData: String, imageHeight: Int, imageWidth: Int): MutableList<SpaceImageLayer> {
        val isSpaceImageEvenlyDivisibleByTheStandardWidth = spaceImageData.length % imageWidth == 0
        val numberOfImageRowsInSpaceImage = spaceImageData.length / imageWidth
        val isImageWhole = isSpaceImageEvenlyDivisibleByTheStandardWidth
                && (numberOfImageRowsInSpaceImage % imageHeight == 0)
        if (!isImageWhole) throw Exception("The image data provided is incomplete")

        val spaceImageLayers = mutableListOf<SpaceImageLayer>()
        var currentIndex = 0
        while (currentIndex < spaceImageData.length - 1) {
            val spaceImageLayer = SpaceImageLayer(imageHeight, imageWidth)
            for (rowNumber in 0 until imageHeight) {
                val imageRowAsString = spaceImageData.substring(currentIndex, currentIndex + imageWidth)
                val imageRow = mutableListOf<Byte>()
                for (letter in imageRowAsString) imageRow.add(letter.toString().toByte())
                spaceImageLayer.imageRows.add(imageRow)
                currentIndex += imageWidth
            }
            spaceImageLayers.add(spaceImageLayer)
            outputLogger.debug("image processed: ${System.lineSeparator()}${spaceImageLayer.getPrintableImage()}")

            outputLogger.debug("currentIndex: $currentIndex")
        }
        return spaceImageLayers
    }

    fun decodeImageFromFile(): String {
        val spaceImageData = fileManager.readFileAsLinesUsingGetResourceAsStream(fileNameContainingSpaceImageData).toMutableList()[0]
        val imageHeight = 6
        val imageWidth = 25
        val resultingSpaceImage = decodeImage(spaceImageData, imageHeight, imageWidth)
        var result = resultingSpaceImage.getPrintableImage()
        result = result.replace('0', ' ').replace('1', '#')
        outputLogger.info("Result: ${System.lineSeparator()}$result")
        return result
    }

    fun decodeImageToString(spaceImageData: String, imageHeight: Int, imageWidth: Int): String {
        return decodeImage(spaceImageData, imageHeight, imageWidth).getPrintableImage()
    }

    fun decodeImage(spaceImageData: String, imageHeight: Int, imageWidth: Int): SpaceImageLayer {
        val spaceImageLayers = processSpaceImageData(spaceImageData, imageHeight, imageWidth)
        val resultingSpaceImage = setupResultingSpaceImage(imageHeight, imageWidth)
        spaceImageLayers.forEach { spaceImageLayer ->
            for (rowIndex in 0 until imageHeight) {
                val imageRow = spaceImageLayer.imageRows[rowIndex]
                for (columnIndex in 0 until imageWidth) {
                    resultingSpaceImage.processPixel(imageRow[columnIndex], rowIndex, columnIndex)
                }
            }
        }
        return resultingSpaceImage
    }

    private fun setupResultingSpaceImage(imageHeight: Int, imageWidth: Int): SpaceImageLayer {
        val resultingSpaceImage = SpaceImageLayer(imageHeight, imageWidth)
        for (rowNumber in 0 until imageHeight) {
            val imageRow = mutableListOf<Byte>()
            for (columns in 0 until imageWidth) {
                imageRow.add(SpaceImageLayer.transparentPixel)
            }
            resultingSpaceImage.imageRows.add(imageRow)
        }
        return resultingSpaceImage
    }

    companion object {
        const val fileNameContainingSpaceImageData = "day8.spaceImage.in"
    }
}

class SpaceImageLayer(val imageHeight: Int, val imageWidth: Int) {
    var imageRows: MutableList<MutableList<Byte>> = ArrayList()
    fun getPrintableImage(): String {
        var result = ""
        for (imageRow in imageRows) {
            result += imageRow.joinToString(prefix = "", postfix = "", separator = "") + System.lineSeparator()
        }
        return result
    }

    fun countOfValue(valueToCount: Byte): Int {
        var zeroCount = 0
        for (imageRow in imageRows)
            for (pixel in imageRow)
                if (pixel == valueToCount) zeroCount++
        return zeroCount
    }

    fun processPixel(pixelValue: Byte, rowIndex: Int, columnIndex: Int) {
        if (rowIndex >= imageHeight || columnIndex >= imageWidth) throw Exception("Invalid index supplied")
        val currentPixelValue = imageRows[rowIndex][columnIndex]
        if (currentPixelValue == transparentPixel) {
            imageRows[rowIndex][columnIndex] = pixelValue
        }
    }

    companion object {
        const val transparentPixel: Byte = 2
    }
}