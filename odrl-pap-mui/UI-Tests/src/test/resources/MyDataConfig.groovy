environments {
    test {
        urlWithoutForwardSlash = "http://localhost:3000"
        urlWithoutForwardSlash = urlWithoutForwardSlash.toLowerCase()
        urlWithForwardSlash = urlWithoutForwardSlash + "/"
    }

    prod {
        urlWithoutForwardSlash = "http://localhost:3000"
        urlWithoutForwardSlash = urlWithoutForwardSlash.toLowerCase()
        urlWithForwardSlash = urlWithoutForwardSlash + "/"
    }
}