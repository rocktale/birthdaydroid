# birthdaydroid

This small app is intended to show a list of upcoming birthdays from the
contact list. Furthermore, a simple widget is planned to get a quick glance
at the next birthdays on the home screen.

## Freatures

* Basic (simplistic) material design
* List birthdays from the phone contacts including
  * Profile picture
  * Name
  * Date
  * Age at the next birthday
* Simple widget based on a list view
* No ads, spyware or secret calls to remote locations

### Roadmap

* Update widget at midnight
* Display remaining days till birthday
* Add localization in English and German
* Improve robustness of contact list parsing
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
