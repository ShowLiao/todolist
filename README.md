# todolist
# Pre-work - TodoList

TodoList is an android app that allows building a todo list and basic todo items management functionality including adding new items, editing and deleting an existing item.

Submitted by: Show Liao

## User Stories

The following **required** functionality is completed:

* [x] User can **successfully add and remove items** from the todo list
* [x] User can **tap a todo item in the list and bring up an edit screen for the todo item** and then have any changes to the text reflected in the todo list.
* [x] User can **persist todo items** and retrieve them properly on app restart

The following **optional** features are implemented:

* [x] Persist the todo items [into SQLite](http://guides.codepath.com/android/Persisting-Data-to-the-Device#sqlite) instead of a text file
* [x] Improve style of the todo items in the list [using a custom adapter](http://guides.codepath.com/android/Using-an-ArrayAdapter-with-ListView)
* [x] Add support for completion due dates for todo items (and display within listview item)
* [x] Use a [DialogFragment](http://guides.codepath.com/android/Using-DialogFragment) instead of new Activity for editing items
* [x] Add support for selecting the priority of each todo item (and display in listview item)
* [x] Tweak the style improving the UI / UX, play with colors, images or backgrounds (I added colors for priority)

The following **additional** features are implemented:
Added Google Map integration when tapping on location of the todo item.

[ ] List anything else that you can get done to improve the app functionality!
Capability to personalize to different themes by the user
Improve UI and user experience

## Video Walkthrough

Here's a walkthrough of implemented user stories:
1.Add a todo item
2.Updating a todo item
3.Deleting an item
3.Select a todo item and long click to open a detail dialog
4.Tap location to open Google Map with location

http://imgur.com/5zA5PTO

<img src='http://i.imgur.com/link/to/your/gif/file.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />

GIF created with [LiceCap](http://www.cockos.com/licecap/).

## Project Analysis

As part of your pre-work submission, please reflect on the app and answer the following questions below:

**Question 1:** "What are your reactions to the Android app development platform so far? Compare and contrast Android's approach to layouts and user interfaces in past platforms you've used.” 

**Answer:** [
My past Android projects at work didn’t need good UI. It was an app that worked with hardware. That app’s main feature to capture the phone’s screen and mirror to another device(display). 

With this app(Todolist), I had to deal with UI objects such as customized dialogs, and placing widget’s objects properly.  SQLite was new too me too.
].

**Question 2:** "Take a moment to reflect on the `ArrayAdapter` used in your pre-work. How would you describe an adapter in this context and what is its function in Android? Why do you think the adapter is important? Explain the purpose of the `convertView` in the `getView` method of the `ArrayAdapter`."

**Answer:** [
ArrayAdapter : I created ItemAdapter extends ArrayAdapter to provide data for ListView  

ListView uses ItemAdapter to fill it’s rows.  ListView calls getView() to populate itself.  

getView: Get a View that displays the data at the specified position in the data set. I also set colors for each priority is High/Mid/Low.
 
convertView : The old view can be reused, if possible. If it’s not possible to convert this view to display the correct data, the method can create a new view.
].

## Notes

Describe any challenges encountered while building the app.

The challenges are objects I haven’t use before. I needed to spend time to build each object’s example to understand how to use them.

In different activity/view/dialog to get context/handle need to think about.

## License

    Copyright [Show Liao] [name of copyright owner]

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
