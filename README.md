# TueboraAssignment
Take home assignment of Tuebora

**Steps to build and run tests :**
1. Fork and clone the project from Github
2. Change Directory (to inside of TueboraAssignment)

**_Build :_**
./gradlew clean build

**_Run tests_**
./gradlew test


**One liner about the important classes for code review**
1. TokenBucketImpl : Implementation of standard token bucket algorithm
2. RateLimitedFunction : This is essentially a wrapper over the Function interface. 
3. RateLimiter : An interface providing functionalities of a generic rate limiter
4. RateLimiterImplWithTokenBucket : An implementation of RateLimiter interface with a custom TokenBucket algorithm
