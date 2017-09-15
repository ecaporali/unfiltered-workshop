# Plan

## About

We are writing an api with 2 endpoints:

```sh
POST: .../imhungry?food=mushed&quantity=2
```
which will get back a requestId
```sh
GET: .../imhungry?requestId=123
```
## About [unfiltered](http://unfiltered.ws)
It's a toolkit on top of various server backends, like java servlet api, jetty servlet, netty framework.
In this workshop we will cover Intents and Directives concepts provided by this lib.

### Intent
Intent is a partial function for handling requests.

```scala
  def intent = {
    case Path("/suchpath") => ResponseString(p)
  }
```

### Directive
Directive is a concept which provides a monadic api on top of unfiltered.
We can leverage a monadic behaivor of directive, by:
 1. spliting the logic of our http handlers into small independent functions.
 2. combining this little functions together in an imperetive way.
 
```scala
def intent = Path.Intent {
  case Seg(List("example", id)) =>
    for {
      _ <- POST
      _ <- contentType("application/json")
      _ <- Accepts.Json
      r <- request[Any]
    } yield Ok ~> JsonContent ~> ResponseBytes(Body bytes r)
}
```

### What's this monadic thing again?
Every directive is essentially a function from HttpRequest to Result[R, A].
Result class provides us a monadic behavior. What does that mean?
Result will be a wrapper of the output of your directive, which can contain either a left or right.
Left is usually representing an Error or a value indicating that some calculation in your function went wrong.
Right is the desired result of your function, when things go the way you expect.

We always have this desire to split our code into independent self-contained testable units.
And we always fight with dependencies, when one unit depends on the outcome of the other units.

Here are the examples of 2 units/functions: getCustomerById(id) and getTaxForCustomer(customer)

```sh
customerOrError = getCustomerById(id)
```

```sh
tax = getTaxForCustomer(customer)
```

The problem here is the fact that getTaxForCustomer will only accept customer. If there was an error in getCustomerById, we should not call the getTaxForCustomer function.
What do we usually do?

```sh
customerOrError = getCustomerById(id)
if (customerOrError is error) {
    return error
}
else {
    tax = getTaxForCustomer(customer)
}
```

What monadic behavior instead gives us:

```sh
taxOrError = getCustomerById(id).flatMap{customer => 
    getTaxForCustomer(customer)
}
```
By `flatMapping` we will only call getTaxForCustomer if getCustomerById returns a valid customer. If there was an error, it will be assigned into the taxOrError variable.

## Outcomes of the workshop:
### 1. Practise writing partial functions
### 2. Compose partial functions with orElse
### 3. Working with monadic behavior of results.
### 4. Glimpse of property-based testing.

## Challenge 1

### Implement Waiter functionality
Run the sbt console first,
```sh
sbt
```
and run the tests
```sh
testOnly com.thoughtworks.WaiterSpec
```

## Challenge 2

### Implement RestaurantHandler
Run the sbt console first,
```sh
sbt
```
and run the tests
```sh
testOnly com.thoughtworks.RestaurantHandlerSpec
```

## Challenge 3
### Add deliveryType field to request

Delivery type can only be 1 of Deliver or Pick up

## How to run

```sh
sbt run
```

### Test it with curl
POST:
```sh
curl -X POST -d food=cheese -d quantity=2 localhost:8080/imhungry
```
expected:

```sh
Your requestID is: b39126af-70a7-4c7c-a88a-a5e627d35635Y
```
GET:
```sh
curl localhost:8080/imhungry?requestId=b39126af-70a7-4c7c-a88a-a5e627d35635
```
expected:
```sh
Your status is: 2 of cheese has been cooked
```

## References
1. [unfiltered](http://unfiltered.ws)
2. [scalacheck](https://www.scalacheck.org)
