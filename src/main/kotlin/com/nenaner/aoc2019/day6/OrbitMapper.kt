package com.nenaner.aoc2019.day6

import com.nenaner.aoc2019.FileManager
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class OrbitMapper(private val fileManager: FileManager) {
    private val logger = LoggerFactory.getLogger(javaClass)

    fun getTotalNumberOfOrbits(orbitMapListingAsString: String): Int {
        return getTotalNumberOfOrbits(orbitMapListingAsString.lines())
    }

    fun getTotalNumberOfOrbitsFromFile(): Int {
        val orbitMapListing = fileManager.readFileAsLinesUsingGetResourceAsStream(fileNameContainingOrbitalMap).toMutableList()
        return getTotalNumberOfOrbits(orbitMapListing)
    }

    fun getMinimumNumberOfOrbitalTransfers(orbitMapListingAsString: String, sourceObjectName: String, targetObjectName: String): Int? {
        return getMinimumNumberOfOrbitalTransfers(orbitMapListingAsString.lines(), sourceObjectName, targetObjectName)
    }

    fun getMinimumNumberOfOrbitalTransfersFromFile(): Int? {
        val orbitMapListing = fileManager.readFileAsLinesUsingGetResourceAsStream(fileNameContainingOrbitalMap).toMutableList()
        return getMinimumNumberOfOrbitalTransfers(orbitMapListing, "YOU", "SAN")
    }

    fun getMinimumNumberOfOrbitalTransfers(orbitMapListing: List<String>, sourceObjectName: String, targetObjectName: String): Int? {
        if (orbitMapListing.isEmpty()) return null
        val centerOfMass = buildOrbitMap(orbitMapListing)
        val rollCallForSource = rollCall(centerOfMass, sourceObjectName)
        logger.debug("Roll call for $sourceObjectName, $rollCallForSource")
        val rollCallForTarget = rollCall(centerOfMass, targetObjectName)
        logger.debug("Roll call for $targetObjectName, $rollCallForTarget")
        val sourceOnlyParents = rollCallForSource.minus(rollCallForTarget)
        val targetOnlyParents = rollCallForTarget.minus(rollCallForSource)
        val result = sourceOnlyParents.size + targetOnlyParents.size
        logger.info("The total number of orbits is: $result")
        return result

    }

    private fun rollCall(centerOfMass: ObjectInSpace, objectName: String): List<String> {
        val result = mutableSetOf<String>()

        var currentObjectBeingTraced = centerOfMass.find(objectName)
                ?: throw Exception("Could not find an object in space with the name of: $objectName")
        var nameOfParent = currentObjectBeingTraced.nameOfParent
        while(nameOfParent != null) {
            result.add(nameOfParent)
            currentObjectBeingTraced = centerOfMass.find(nameOfParent)
            ?: throw Exception("Could not find an object in space with the name of: $objectName")
            nameOfParent = currentObjectBeingTraced.nameOfParent
        }
        return result.reversed()
    }

    private fun getTotalNumberOfOrbits(orbitalPairs: List<String>): Int {
        if (orbitalPairs.isEmpty()) return 0

        val centerOfMass = buildOrbitMap(orbitalPairs)

        val result = centerOfMass.getTotalNumberOfChildOrbits()
        logger.info("The total number of orbits is: $result")
        return result
    }

    private fun buildOrbitMap(orbitalPairs: List<String>): ObjectInSpace {
        var objectInSpaceListing = createObjectInSpaceListing(orbitalPairs)

        val objectsWithNoParent = objectInSpaceListing.filter { it.nameOfParent == null }
        while (objectsWithNoParent.size > 1) {
            logger.info("${objectsWithNoParent.size} object remain not orbiting a parent")
            objectInSpaceListing = createObjectInSpaceListing(orbitalPairs)
        }

        val centerOfMass = objectInSpaceListing.find { it.nameOfParent == null }
                ?: throw Exception("The center of mass could not be found")
        return centerOfMass
    }

    private fun createObjectInSpaceListing(orbitalPairs: List<String>): MutableList<ObjectInSpace> {
        val objectInSpaceListing = mutableListOf<ObjectInSpace>()
        orbitalPairs.forEach { orbitalPair ->
            val (nameOfParent, nameOfChild) = getOrbitalPairNames(orbitalPair)

            var parentObject = objectInSpaceListing.find { it.name == nameOfParent }
            if (parentObject == null) {
                parentObject = ObjectInSpace(nameOfParent)
                objectInSpaceListing.add(parentObject)
            }

            var childObject = objectInSpaceListing.find { it.name == nameOfChild }
            if (childObject == null) {
                childObject = ObjectInSpace(nameOfChild)
                objectInSpaceListing.add(childObject)
            }

            if (childObject.nameOfParent != null && childObject.nameOfParent != nameOfParent)
                throw Exception("$nameOfChild cannot orbit $nameOfParent when it already is in orbit of ${childObject.nameOfParent}")

            childObject.nameOfParent = nameOfParent
            parentObject.addChildObject(childObject)

        }
        return objectInSpaceListing
    }

    private fun getOrbitalPairNames(orbitalPairString: String): Pair<String, String> {
        val pairedOrbitDataArray = orbitalPairString.split(')')
        if (pairedOrbitDataArray.size != 2) throw Exception("SourceData invalid when parsing '$orbitalPairString'")
        return Pair(pairedOrbitDataArray[0], pairedOrbitDataArray[1])
    }

    companion object {
        const val fileNameContainingOrbitalMap = "day6.orbitMap.in"
    }
}

class ObjectInSpace(val name: String, var nameOfParent: String? = null) {
    private val logger = LoggerFactory.getLogger(javaClass)

    private var children = mutableListOf<ObjectInSpace>()

    fun addChildObject(child: ObjectInSpace) {
        val existingChild = children.find { it.name == child.name }
        if (existingChild == null) {
            logger.debug("Adding ${child.name} as a child of $name")
            children.add(child)
        }
    }

    fun find(nameOfObjectToFind: String): ObjectInSpace? {
        if (nameOfObjectToFind == name) return this
        children.forEach {
            val result = it.find(nameOfObjectToFind)
            if (result != null) return result
        }
        return null
    }

    fun getTotalNumberOfChildOrbits(): Int {
        var total = 0
        children.forEach {
            total += it.getTotalNumberOfOrbits(1)
        }
        return total
    }

    private fun getTotalNumberOfOrbits(numberOfParents: Int): Int {
        var total = numberOfParents
        children.forEach {
            total += it.getTotalNumberOfOrbits(numberOfParents + 1)
        }
        return total
    }
}