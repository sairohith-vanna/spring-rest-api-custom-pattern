# Spring Boot Custom REST API Pattern

The REST APIs of a product that are built should be designed as though they are Public APIs, even if they absolutely are not. The response should be expressive and indicative of successful or otherwise in as detail as possible.

This repository contains a simple Spring Boot 3.1 application, which doesn't serve any more purpose than demonstrating different elements we could possibly include in the response of the API, in success and error scenarios. In addition to that, it introduces a pattern that drastically reduces the boilerplate and repetitive code across REST controllers without having to build the detailed responses every time an endpoint method has to return a response.

Elements of the API Responses this codebase introduces:

- `requestId` - to track the request that can be used for running the diagnostics
- `correlationId` - if the request involves processing across multiple services, this is a valuable identifier to trace the workflow across integrations
- `timestamp` - time of the request completion
- `data` - the data that the response returns, such as an entity object or a DTO. The type of value is derived from the generic parameter of `APIResponse`
- `message` - any message that the API wants to respond with
- `errorCode` - an error code to indicate what family of errors the error response belongs to
- `errors` - list of errors reported by the API
- `success` - indicates whether the response is successful or not (when there are errors)

### Exception Types

There are two abstract `Exception` classes that subclass `Exception` and add necessary details that `APIResponse` can pick up from. This simplifies the build up of the `APIResponse` object. 

- `ApplicationAbstractException` - Adds two fields `statusCode` and `errorCode`: (a) `statusCode` helps the controller determine what status code the response should contain in error scenarios and (b) `errorCode` as described above helps determine the error code of the response
- `TraceableApplicationAbstractException` - Adds three fields `requestId`, `correlationId` and `timestamp` to determine the values accordingly as specified in the above elements description. This can. be used when the request has to traced for diagnostic purposes

### The Base Abstract REST Controller

`RestControllerBase` is an abstract controller that serves as a base class for all the REST controllers. This will reduce the boilerplate needed to build `ResponseEntity<APIResponse<T>>` type. It provides a few helper methods, `ok()`, `success()`, `error()` in different overloads, for different scenarios. The controllers inheriting this class have the luxury to simply invoke any of the methods by just passing a parameter or two to build a sophisticated response with the aforementioned field elements.

Here's an example from the codebase, where we catch `ApplicationAPIException`, which is a subclass of `TraceableApplicationAbstractException`. All we have to do is call the inherited `RestControllerBase::error` overload method that accepts an instance of `TraceableApplicationAbstractException`. The base method fulfills the responsbility of building the appropriate response as `ResponseEntity<APIResponse<?>>` type.

```java
@GetMapping("error")
public ResponseEntity<?> getTraceableError() {
    try {
        applicationService.throwExceptionOnCall();
        return null;
    } catch (ApplicationAPIException e) {
        return error(e);
    }
}
```
