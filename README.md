# CS 30700: Product Backlog                 Purdue Bar App


## Problem Statement

Whenever you want to go out with your friends it is hard to decide where you want to go, what places are busy, and who will be the drivers. We want to make this task easier, and make sure people are being safe. 


## Background Information

There are many different things to consider when trying to plan the most fun night possible. You must answer the questions of where to go, who to go with, what places are busy, who will drive and so on. College students and young adults make these decisions every time they go out with their friends and right now all they use to make plans are groupme or other texting services. There is no one app that makes planning nights out easy and efficient. With this app you can find out who is going out, what specials various places are having on any given night and even a find your friends part to see who else is near you that you might know. 

## Requirement Backlog

### Functional
 
* As a user, I would like to create and edit an account/profile through Google/Facebook.
* As a user, I would like to find friends and request to be their friend.
* As a user, I would like to message my friends, either individually or in groups.
* As a user, I would like to create a poll to decide where to go that night.
* As a user, I would like to be able to know where my friends are if they allow me to.
* As a user, I would like to have access to information about local bars, like it’s address, menus, daily specials.
* As a user, I would like to have the ability to see if a certain bar is busy. 
* As a user, I would like to update a bar’s busy status. (Like Waze)
* As a user, I would like the ability to split drink prices, for example. (Splitwise)
* As a user, I would like to have access to a pick-up line generator.
* As a user, I would like to keep track of who is driving, and make sure the same person isn’t driving all the time. 
* As a user, I would like the interface to be friendly and intuitive.
* As a user, I would like the app to be secure.
* As a user, I would like to get directions to the chosen bar.
* As a bar owner, I would like to have special privileges when updating my own bar (if time allows).
* As a bar owner, I would like to be able to update the bar’s menu and daily specials.
* As a bar owner, I would like to update the bar’s busy status.
* As a developer, I would like user feedback.
* As a developer, I would like to manage all users.
* As a developer, I would like to make sure the user has an internet connection.
* As a developer, I would like for there to be little to no lag when updates occur.
* As a developer, I would like the app to be able to handle many users.

### Non-Functional

* Allow individuals to log on with Google or Facebook using authentications given by the companies. (Google/Facebook OAuth2) For authenticity purposes
* Users will manually add friends to groups in order to negate random people joining a group
* For the functionality of “find my friend” feature, allow friends to turn on or off while still in group.
* Encrypt backend data before sending to client to reduce interception risks
* Use a backend database that we can reuse no matter what happens to the front end
* Make app fast and responsive. Users should not have to wait to look up drink specials
* Make polls intuitive and easy to use
* Want to have the ability to be able to scale to more bars, not just Purdue and not just college campus
* Maybe be able to even scale to restaurants, ie see what’s busy, poll for who wants to go where, etc.
* Allow later integration with the android purdue app’s bus integration.Josh
