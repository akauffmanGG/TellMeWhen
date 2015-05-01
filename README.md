# TellMeWhen
Listens for plain English date phrases and returns a matching date object. For example: "Next Thursday" or "Last Year".

The TellMeWhen class has a static listen method that takes in a phrase. Passing the phrase by itself will return a date relative to the current date. If you call listen with a phrase and a Date parameter , then it will use the given date as a reference point.

For example the phrase: "This April" will return a date for April relative to the current date. However, if you pass the phrase along with a date of March 1st, 2012, then it will return the date of April 1st, 2012.

Here are more examples of supported phrases:

Next Hour

Last Week

Previous Saturday

Last Day of the Week

First Week of the Month

Next Jan

Yesterday
