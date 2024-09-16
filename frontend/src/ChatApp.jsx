
import React, { useState, useEffect } from 'react';
import './ChatApp.css'; 
const ChatApp = () => {
  const [socket, setSocket] = useState(null);
  const [messages, setMessages] = useState([]);
  const [messageInput, setMessageInput] = useState('');
  const [name] = useState(localStorage.getItem('name') || '');
  const [typingUser, setTypingUser] = useState('');

  useEffect(() => {
    const newSocket = new WebSocket('ws://localhost:8086/chat'); 

    newSocket.onopen = () => {
      console.log('WebSocket connection opened');
    };

    newSocket.onmessage = (event) => {
      const data = JSON.parse(event.data);

      switch (data.type) {
        case 'message':
          setMessages([...messages, { name: data.name, text: data.text, id: data.id }]);
          break;
        case 'typing':
          setTypingUser(data.name);
          break;
        case 'seen':
          setMessages(
            messages.map((msg) => (msg.id === data.id ? { ...msg, seenBy: data.name } : msg))
          );
          break;
        default:
          break;
      }
    };

    setSocket(newSocket);

    return () => {
      newSocket.close();
    };
  }, [messages]);

  const sendMessage = () => {
    if (messageInput.trim() !== '') {
      const newMessage = { type: 'message', name, text: messageInput };
      socket.send(JSON.stringify(newMessage));
      setMessageInput('');
    }
  };

  const handleTyping = () => {
    const typingMessage = { type: 'typing', name };
    socket.send(JSON.stringify(typingMessage));
  };

  // const handleSeen = (id) => {
  //   const seenMessage = { type: 'seen', id, name };
  //   socket.send(JSON.stringify(seenMessage));
  // };

  

  return (
    <div className="chat-container">
      <ul className="messages-list">
        {messages.map((msg) => (
          <li key={msg.id} className="message-item">
            <span>{msg.name}:</span> {msg.text}
            {msg.seenBy && <span> (seen by {msg.seenBy})</span>}
          </li>
        ))}
      </ul>
      <div className="input-container">
        <input
          className="input-field"
          type="text"
          value={messageInput}
          onChange={(e) => setMessageInput(e.target.value)}
          onKeyDown={handleTyping}
        />
        <button onClick={sendMessage}>Send</button>
      </div>
      {typingUser && <div className="typing-indicator">{typingUser} is typing...</div>}
    </div>
  );
};

export default ChatApp;
