package com.busem.data.common

import java.io.IOException

class UnauthorizedException(message: String) : IOException(message)
class SocketTimeoutException(message: String) : IOException(message)
class ClientException(message: String) : IOException(message)
class ServerException(message: String) : IOException(message)