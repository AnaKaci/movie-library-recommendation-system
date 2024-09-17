import * as TYPES from '../actions/types';

const initialState = {
    friends: [],
    mutualFriends: false,
    loading: false,
    error: null,
};

const friendReducer = (state = initialState, action) => {
    switch (action.type) {
        case TYPES.ADD_FRIEND_REQUEST:
        case TYPES.GET_ALL_FRIENDS_REQUEST:
        case TYPES.REMOVE_FRIEND_REQUEST:
        case TYPES.GET_MUTUAL_FRIENDS_REQUEST:
            return {
                ...state,
                loading: true,
                error: null,
            };
        case TYPES.ADD_FRIEND_SUCCESS:
            return {
                ...state,
                friends: [...state.friends, action.payload],
                loading: false,
            };
        case TYPES.GET_ALL_FRIENDS_SUCCESS:
            return {
                ...state,
                friends: action.payload,
                loading: false,
            };
        case TYPES.GET_MUTUAL_FRIENDS_SUCCESS:
            return {
                ...state,
                mutualFriends: action.payload,
                loading: false,
            };
        case TYPES.REMOVE_FRIEND_SUCCESS:
            return {
                ...state,
                friends: state.friends.filter(friend => friend.id !== action.payload.friendId),
                loading: false,
            };
        case TYPES.ADD_FRIEND_FAILURE:
        case TYPES.GET_ALL_FRIENDS_FAILURE:
        case TYPES.REMOVE_FRIEND_FAILURE:
        case TYPES.GET_MUTUAL_FRIENDS_FAILURE:
            return {
                ...state,
                loading: false,
                error: action.payload,
            };
        default:
            return state;
    }
};

export default friendReducer;
