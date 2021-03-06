# birthdaydroid

This small app is intended to show a list of upcoming birthdays from the
contact list. Furthermore, a simple widget is provided to get a quick glance
at the next birthdays on the home screen.

So far, the app only speaks German.

## Freatures

* Basic (simplistic) material design
* List birthdays from the phone contacts including
  * Profile picture
  * Name
  * Date
  * Age at the next birthday
  * Remaining days till birthday as a toast message when clicked
* Simple widget based on a list view
  * With automatic update shortly after midnight
* No ads, spyware or secret calls to remote locations

### Roadmap

* Add localization in English and German
* Improve robustness of contact list parsing
* Replace "remaining days till birthday" toast with a detail view of the the contact, including a larger image, the remaining days and some quick links to send a message
* Add notifications on or before birthdays (optional)

## Supported platforms

This app requires Android SDK version 26 since it uses `java.time` for all
the date related operations. Therefore, it can only be used on Android 8.0
and newer. So far, it is only tested on a Samsung S7 running Android 8.0 and on the
Android 8.1 emulator image. I have no idea if other devices work as well.

## Use at own risk

This is my first Android app ever and my first Java app in a very long time.
Therefore, it serves as a playground to get familiar with some of the basic
concepts and might not even be close to the optimal / right way to do things.
It will most probably change a lot.
