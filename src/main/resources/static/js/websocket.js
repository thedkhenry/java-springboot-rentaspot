const URL = 'http://localhost:8080';
var stompClient = null;
let selectedUser;

$(document).ready(function() {
    console.log("Page is ready");
    connect();
    $("#send").click(function() {
        sendMessage();
    });
    $('#message').keypress(function(event){
        var keycode = (event.keyCode ? event.keyCode : event.which);
        if(keycode == '13'){
            sendMessage();
        }
    });
});

function connect() {
    let socket = new SockJS('/my-stomp-endpoint');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected! ' + frame);
        stompClient.subscribe('/user/chat/messages', function (message) {
            console.log('OnReceived: ' + message);
            renderMessage(JSON.parse(message.body));
        });
    },function (err) {
        console.log(err);
    });
}

function selectChatUser(userId){
    console.log("Selecting chat user... " + userId);
    if(selectedUser === userId){
        return;
    }
    if (selectedUser !== undefined) {
        $('#chatUser' + selectedUser).removeClass('active text-white');
    }
    $('#chatUser' + userId).addClass('active text-white');
    selectedUser = userId;
    getMessages(userId);
}

function sendMessage() {
    if(selectedUser === undefined){
        return;
    }
    const message = {
        messageContent: $("#message").val(),
    };
    if (message.messageContent.trim() !== "") {
        console.log("Sending message...");
        stompClient.send("/app/message/" + selectedUser, {}, JSON.stringify(message));
        $("#message").val('');
        renderMessage(message);
    }
}

function renderMessage(message) {
    console.log("Rendering message... " + message.messageContent);
    let msgContent = message.messageContent;
    if(selectedUser === undefined){
        return;
    }
    if (message.senderId === selectedUser) {
        $("#messageList").append(
            "<li class=\"list-group-item mw-100 text-break border-0 d-flex\">" +
            "   <div class=\"rounded p-2 bg-danger text-white\">" +
            "       <div>" + msgContent + "</div>" +
            "   </div>" +
            "</li>");
    }else {
        $("#messageList").append(
            "<li class=\"list-group-item mw-100 text-break border-0 d-flex justify-content-end ms-auto\">" +
            "   <div class=\"rounded p-2 bg-light\">" +
            "       <div>" + msgContent + "</div>" +
            "   </div>" +
            "</li>");
    }
    $("#messageList")[0].scrollIntoView({behavior: "smooth",block: "end"});
}

function getMessages(receiverId) {
    console.log("Fetching messages..." + receiverId);
    $('#messageList').empty();
    $.ajax({
        type: "GET",
        url: URL + "/fetch-messages/"+receiverId,
        success: function (data) {
            console.log("SUCCESS : ", data);
            for(let i = 0; i < data.length; i++){
                renderMessage(data[i]);
            }
        },
        error: function (e) {
            console.log("ERROR : ", e);
        }
    });

}