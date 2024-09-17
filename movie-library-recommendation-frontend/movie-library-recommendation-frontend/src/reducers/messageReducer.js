import * as TYPES from '../actions/types';

const initialState = {
    messagesFromSender: [],
    messagesFromReceiver: [],
    loading: false,
    error: null,
};

const messageReducer = (state = initialState, action) => {
    switch (action.type) {
        case TYPES.FETCH_MESSAGES_FROM_SENDER_REQUEST:
        case TYPES.FETCH_MESSAGES_FROM_RECEIVER_REQUEST:
            return {
                ...state,
                loading: true,
                error: null,
            };
        case TYPES.FETCH_MESSAGES_FROM_SENDER_SUCCESS:
            return {
                ...state,
                loading: false,
                messagesFromSender: action.payload,
            };
        case TYPES.FETCH_MESSAGES_FROM_RECEIVER_SUCCESS:
            return {
                ...state,
                loading: false,
                messagesFromReceiver: action.payload,
            };
        case TYPES.FETCH_MESSAGES_FROM_SENDER_FAILURE:
        case TYPES.FETCH_MESSAGES_FROM_RECEIVER_FAILURE:
            return {
                ...state,
                loading: false,
                error: action.payload,
            };
        case TYPES.ADD_MESSAGE:
            return {
                ...state,
                messagesFromSender: [...state.messagesFromSender, action.payload],
                messagesFromReceiver: [...state.messagesFromReceiver, action.payload],
            };
        case TYPES.SET_MESSAGES:
            return {
                ...state,
                messagesFromSender: action.payload.updatedMessagesFromSender,
                messagesFromReceiver: action.payload.updatedMessagesFromReceiver,
            };
        default:
            return state;
    }
};

export default messageReducer;
