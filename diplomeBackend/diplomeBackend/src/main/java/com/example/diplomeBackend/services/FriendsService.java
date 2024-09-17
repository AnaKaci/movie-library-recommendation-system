package com.example.diplomeBackend.services;

import com.example.diplomeBackend.models.Friends;
import com.example.diplomeBackend.models.User;
import com.example.diplomeBackend.repositories.FriendsRepository;
import com.example.diplomeBackend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FriendsService {

    @Autowired
    private FriendsRepository friendsRepository;

    @Autowired
    private UserRepository userRepository;

    public Friends addFriend(Long userId, Long friendId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + userId));
        User friend = userRepository.findById(friendId)
                .orElseThrow(() -> new UsernameNotFoundException("Friend not found with id: " + friendId));
        Friends friends = friendsRepository.findByUser(user)
                .orElseGet(() -> new Friends(user));
        friends.getFriends().add(friend);
        return friendsRepository.save(friends);
    }

    public void removeFriend(Long userId, Long friendId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + userId));
        User friend = userRepository.findById(friendId)
                .orElseThrow(() -> new UsernameNotFoundException("Friend not found with id: " + friendId));
        Optional<Friends> friendsOptional = friendsRepository.findByUser(user);

        if (friendsOptional.isPresent()) {
            Friends friends = friendsOptional.get();
            friends.getFriends().remove(friend);
            friendsRepository.save(friends);
        }
    }
    public List<User> getAllFriends(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + userId));
        return friendsRepository.getAllUserFriends(user);
    }

    public boolean areMutualFriends(Long senderId, Long receiverId) {

        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + senderId));
        User receiver = userRepository.findById(receiverId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + receiverId));

        List<User> senderFriends = friendsRepository.getAllUserFriends(sender);
        List<User> receiverFriends = friendsRepository.getAllUserFriends(receiver);

        boolean senderHasReceiverAsFriend = senderFriends.stream()
                .anyMatch(friend -> friend.getUserId().equals(receiver.getUserId()));

        boolean receiverHasSenderAsFriend = receiverFriends.stream()
                .anyMatch(friend -> friend.getUserId().equals(sender.getUserId()));

        return senderHasReceiverAsFriend && receiverHasSenderAsFriend;
    }

    public List<User> getMutualFriends(User sender, User receiver) {

        List<User> senderFriends = friendsRepository.getAllUserFriends(sender);
        List<User> receiverFriends = friendsRepository.getAllUserFriends(receiver);

        return senderFriends.stream()
                .filter(senderFriend -> receiverFriends.stream()
                        .anyMatch(receiverFriend -> receiverFriend.getUserId().equals(senderFriend.getUserId())))
                .collect(Collectors.toList());

    }




}
