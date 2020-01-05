package com.nenaner.aoc2019

class OpCodeWithParameterModes(val opCode: OpCode,
                               val firstParameterMode: ParameterMode,
                               val secondParameterMode: ParameterMode,
                               val thirdParameterMode: ParameterMode
) {

    companion object {
        fun generateOpCodeWithParameterModes(opCodeInstruction: Long): OpCodeWithParameterModes {
            val thirdParameterMode: ParameterMode = ParameterMode.values()[opCodeInstruction.toInt() / 10000]
            val secondParameterMode: ParameterMode = ParameterMode.values()[opCodeInstruction.toInt() / 1000 % 10]
            val firstParameterMode: ParameterMode = ParameterMode.values()[opCodeInstruction.toInt() / 100 % 10]
            val temp = opCodeInstruction.toInt() % 100
            val opCode: OpCode = opCodeMap[temp] ?: throw Exception("The OpCode provided is invalid. opCodeInstruction: $opCodeInstruction")

            val result = OpCodeWithParameterModes(opCode, firstParameterMode, secondParameterMode, thirdParameterMode)
            return result
        }
    }
}