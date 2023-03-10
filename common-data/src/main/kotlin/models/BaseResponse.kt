package models

interface BaseResponse<T> {
    val code: Int
    val message: String
    val result: T?
}