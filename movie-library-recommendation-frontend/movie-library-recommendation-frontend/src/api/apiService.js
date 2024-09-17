import axios from 'axios';

const apiClient = axios.create({
  baseURL: 'http://localhost:8082/',
  headers: {
    'Content-Type': 'application/json',
  },
});


// Configuration with the Spring Backend
export const fetchConfiguration = () => {
  return apiClient.get('/api/configuration');
};


// Genre api services
export const fetchGenres = () => {
  return apiClient.get('/api/genres');
};

export const fetchMoviesByGenre = (genreName) => {
  return apiClient.get(`/api/genres/${genreName}/movies`);
};



// User api services
export const fetchUserById = (id) => {
  return apiClient.get(`/api/user/${id}`);
}

export const logoutUser = () => {
  return apiClient.post('/api/logout'); // No parameters needed
};

export const deleteUser = (id) => {
  return apiClient.delete(`/api/user/${id}`);
};


export const updateProfile = async (id, updatedUser) => {
  try {
    const response = await apiClient.put(`/api/user/update/${id}`, updatedUser);
    return response.data;
  } catch (error) {
    if (error.response) {
      console.error('Error response:', error.response.data);
      throw new Error(error.response.data || 'An error occurred updating user');
    } else if (error.request) {
      console.error('Error request:', error.request);
      throw new Error('No response received from the server');
    } else {
      console.error('Error message:', error.message);
      throw new Error(error.message || 'An unexpected error occurred');
    }
  }
};




// Preference api services
export const fetchAllActors = () => {
  return apiClient.get('/api/getactors');
};

export const fetchAllDirectors= () => {
  return apiClient.get('/api/getdirectors');
};

export const fetchGenresForPreference= () => {
  return apiClient.get('/api/getgenres');
};





// Feedback api services
export const addFeedback = async (userId, movieId, feedbackText, feedbackRating) => {
  const requestBody = {
    userId,
    movieId,
    feedbackText,
    feedbackRating
  };
  try {
    const response = await apiClient.post(`/api/rate/add`, requestBody);
    return response.data;
  } catch (error) {
    if (error.response) {
      console.error('Error response:', error.response.data);
      throw new Error(error.response.data || 'An error occurred while adding feedback');
    } else if (error.request) {
      console.error('Error request:', error.request);
      throw new Error('No response received from the server');
    } else {
      console.error('Error message:', error.message);
      throw new Error(error.message || 'An unexpected error occurred');
    }
  }
};

export const getFeedbacks = (movieId) => {
  return apiClient.get(`/api/rate/feedback/${movieId}`);
}

export const getFeedbacksUser = (userId, movieId) => {
  return apiClient.get(`/api/rate/feedback/${userId}/${movieId}`);
}

export const updateFeedback = async (userId, movieId, feedbackText, feedbackRating) => {
  const requestBody = {
    userId,
    movieId,
    feedbackText,
    feedbackRating
  };
  try {
    const response = await apiClient.put(`/api/rate/update/${userId}/${movieId}`, requestBody);
    return response.data;
  } catch (error) {
    if (error.response) {
      console.error('Error response:', error.response.data);
      throw new Error(error.response.data || 'An error occurred while updating feedback');
    } else if (error.request) {
      console.error('Error request:', error.request);
      throw new Error('No response received from the server');
    } else {
      console.error('Error message:', error.message);
      throw new Error(error.message || 'An unexpected error occurred');
    }
  }
};

export const deleteFeedback = async (userId, movieId) => {
  try {
    const response = await apiClient.delete(`api/rate/delete/${userId}/${movieId}`);
    return response.data;
  } catch (error) {
    console.error('Error deleting feedback:', error);
    throw error;
  }
};





// Friends api services
export const addFriend = (userId, friendId) => {
  return apiClient.post(`/api/user/${userId}/friends/${friendId}`);
};

export const getAllFriends = (userId) => {
  return apiClient.get(`/api/user/${userId}/friends`);
};

export const removeFriend = (userId, friendId) => {
  return apiClient.delete('/api/remove/friend', {
    params: { userId, friendId }
  });
};

export const getMutualFriends = (senderId, receiverId) => {
  return apiClient.get('/api/mutual-friends', {
    params: {
      senderId: senderId,
      receiverId: receiverId,
    },
  });
};





// Messages api services
export const getMessages = async (senderId, receiverId) => {
  try {
    const response = await apiClient.get(`/api/messages/conversation/${senderId}/${receiverId}`);
    return response.data;
  } catch (error) {
    throw new Error('Failed to fetch messages');
  }
};


