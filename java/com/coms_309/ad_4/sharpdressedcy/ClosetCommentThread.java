package com.coms_309.ad_4.sharpdressedcy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * This accesses the chat room for the application.
 */
public class ClosetCommentThread extends AppCompatActivity {

    Button  sendButton;
    EditText message;
    TextView chat;
    String userID;
    Button homeButton;
    String[] messages;

    private WebSocketClient mWebSocketClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_closet_comment_thread);
        sendButton          = (Button) findViewById(R.id.sendButton);
        TextView userIDText = (TextView) findViewById(R.id.userID);
        message             = (EditText) findViewById(R.id.messageText);
        chat                = (TextView) findViewById(R.id.chat);
        homeButton          = (Button) findViewById(R.id.homeButton);
        messages = new String[] {"","","","",""};

        // This gets the UserID from the previous class
        userID = getIntent().getStringExtra("userID");
        userIDText.setText(userID);

        // This connects the phone to the server.
        connectWebSocket();

        // This sets up the listener to send a message whenever clicked.
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });

        // This listens for the "Home" button to be clicked
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goHome();
            }
        });
    }

    /**
     * This connects the web socket initially. When it goes through this, it creates a listener for
     * any messages from the server. In addition, it allows for the client (a.k.a. the phone) to
     * send messages at any time.
     */
    private void connectWebSocket() {
        URI uri;
        try {
            uri = new URI("ws://proj309-ad-04.misc.iastate.edu:8080/websocket/"+userID);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return;
        }

        mWebSocketClient = new WebSocketClient(uri) {
            @Override
            public void onOpen(ServerHandshake serverHandshake) {
                Log.i("Websocket", "Opened");
            }

            @Override
            public void onMessage(String s) {
                final String message = s;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String printMsg = "";
                        for(int i=0; i<4; i++) {
                            messages[i] = messages[i+1];
                            printMsg += messages[i];
                        }
                        messages[4] = message+"\n";
                        printMsg += messages[4];
                        chat.setText(printMsg);
                    }
                });
            }

            @Override
            public void onClose(int i, String s, boolean b) {
                Log.i("Websocket", "Closed " + s);
            }

            @Override
            public void onError(Exception e) {
                Log.i("Websocket", "Error " + e.getMessage());
            }
        };
        mWebSocketClient.connect();
    }

    /**
     * This sends a new message to the server and resets the message string.
     */
    public void sendMessage() {
        String messageText = userID + ": " + message.getText().toString();
        chat.setText(chat.getText() + "\n");
        mWebSocketClient.send(messageText);
        message.setText("");
    }

    /**
     * When this button is clicked, it will exit without saving and go to the home page
     */
    private void goHome() {
        mWebSocketClient.close();
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra("userID", userID);
        startActivity(intent);
    }
}
