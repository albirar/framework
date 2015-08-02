Introduction
============

Albirar uses his framework to develop applications. The most interesting module of this framework is **dynabean** module. **DynaBean** enables aspect oriented modeling to avoid the problems with inheritance, and to enable to encapsulate and reuse pieces of models. Another good feature of this module is that the javaBean implementation model is made in runtime, so different services use the same aspects of model although they are modeled with another aspects not defined when service was conceived.

By example, if a service use a IRegistered to define a model as:

* User id
* active
* Roles

Another service can use them to extend and enhance with another aspects like:

* password
* email
* Name
* Surname
* ...

