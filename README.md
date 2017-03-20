# Scala Dropbox Demo

This is a simple Scala demo for interacting with the [Dropbox v2 API](https://www.dropbox.com/developers/documentation/java).
It is a combination of an updated version of the demo in [this gist](https://gist.github.com/kmader/66d4772916c89b5cd40a), which targeted v1 of the API, and the [Java tutorial](https://www.dropbox.com/developers/documentation/java#tutorial) on the official site.
This demonstration is pursuant to research for [azavea/raster-foundry#1242](https://github.com/azavea/raster-foundry/issues/1242)

## Instructions

 1. Create an app in Dropbox here: https://www.dropbox.com/developers/apps/create
 2. Populate [`Credentials.scala`](https://github.com/rajadain/scala-dropbox-demo/blob/master/src/main/scala/Credentials.scala) with your `app_key` and `app_secret`
 3. Compile and run the app `./sbt run`
 4. Click the link in the output, and paste in your OAuth Code, and continue

## Output

The app should list your user name, upload a file from the project, list all files found in `Dropbox/Apps/<your-app-name>/`, and download the test upload file.

The output should look something like this:

```
[info] Running com.tuhinanshu.scala.Main
1. Go to: https://www.dropbox.com/oauth2/authorize?response_type=code&client_id=YOUR_CLIENT_ID
2. Click 'Allow' (you may have to log in)
3. Copy the authrozation code and paste here:
PASTE_YOUR_ACCESS_CODE_HERE
Access Token = THIS_SESSIONS_ACCESS_TOKEN_WILL_BE_SHOWN_HERE
==> TEST BASIC ACCOUNT DETAILS
Linked Account is Terence Tuhinanshu
7998827699 of 11005853696 bytes available
==> TEST UPLOADING LOCAL FILE
Uploaded file /jabberwocky.txt
==> TEST LISTING FILES
/jabberwocky.txt
==> DOWNLOAD ITEM TEST
Downloaded file Success({".tag":"file","name":"jabberwocky.txt","id":"id:EfB_jqT5XfAAAAAAAAAAAw","client_modified":"2017-03-13T21:38:37Z","server_modified":"2017-03-13T21:38:38Z","rev":"155490852","size":936,"path_lower":"/jabberwocky.txt","path_display":"/jabberwocky.txt"})
[success] Total time: 17 s, completed Mar 13, 2017 6:32:00 PM
```

## Demo

![image](https://cloud.githubusercontent.com/assets/1430060/23876630/cac321a0-0814-11e7-94fc-55dbe229a052.png)
