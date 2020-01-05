package com.nenaner.aoc2019

enum class ParameterMode(val value: Int) {
    POSITION_MODE(0),
    IMMEDIATE_MODE(1),
    RELATIVE_MODE(2)
}

val opCodeMap = OpCode.values().associateBy(OpCode::value)
enum class OpCode(val value: Int) {
    ADDITION(1),
    MULTIPLICATION(2),
    INPUT(3),
    OUTPUT(4),
    JUMP_IF_TRUE(5),
    JUMP_IF_FALSE(6),
    LESS_THAN(7),
    EQUALS(8),
    RELATIVE_BASE_ADJUSTMENT(9),
    TERMINATION(99)
}