# class registerer
## how to use
1. you need maven for this
1. clone repo
1. edit registerer.java under main > java > registerer to have date and time you want to register
1. run registerer.java passing in netid and password in String[] args
## updates
- create and submit backup schedule (permutations)
- repeated scrape to beat notify uw
### how it works
basically i use selenium to do the work and i schedule the task to run on a thread using executors