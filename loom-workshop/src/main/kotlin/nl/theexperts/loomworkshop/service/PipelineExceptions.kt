package nl.theexperts.loomworkshop.service

class DataFetchException(message: String) : RuntimeException(message)
class DataNotFoundException(message: String) : RuntimeException(message)