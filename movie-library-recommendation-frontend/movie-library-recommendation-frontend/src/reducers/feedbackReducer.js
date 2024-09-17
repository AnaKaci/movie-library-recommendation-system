import * as TYPES from "../actions/types";

const initialState = {
    loading: false,
    feedback: [],
    userFeedback: [],
    error: null,
};

const feedbackReducer = (state = initialState, action) => {
    switch (action.type) {
        case TYPES.ADD_FEEDBACK_REQUEST:
            return {
                ...state,
                loading: true,
                error: null,
            };
        case TYPES.ADD_FEEDBACK_SUCCESS:
            return {
                ...state,
                loading: false,
                feedback: [...state.feedback, action.payload],
                error: null,
            };
        case TYPES.ADD_FEEDBACK_FAILURE:
            return {
                ...state,
                loading: false,
                error: action.payload,
            };
        case TYPES.GET_FEEDBACKS_REQUEST:
            return {
                ...state,
                loading: true,
                error: null,
            };
        case TYPES.GET_FEEDBACKS_SUCCESS:
            return {
                ...state,
                loading: false,
                feedback: action.payload,
                error: null,
            };
        case TYPES.GET_FEEDBACKS_FAILURE:
            return {
                ...state,
                loading: false,
                error: action.payload,
                feedback: [],
            };
        case TYPES.GET_USER_FEEDBACKS_REQUEST:
            return {
                ...state,
                loading: true,
                error: null,
            };
        case TYPES.GET_USER_FEEDBACKS_SUCCESS:
            return {
                ...state,
                loading: false,
                userFeedback: action.payload,
                error: null,
            };
        case TYPES.GET_USER_FEEDBACKS_FAILURE:
            return {
                ...state,
                loading: false,
                error: action.payload,
                userFeedback: [],
            };
        case TYPES.UPDATE_FEEDBACK_REQUEST:
            return {
                ...state,
                loading: true,
                error: null,
            };
        case TYPES.UPDATE_FEEDBACK_SUCCESS:
            return {
                ...state,
                loading: false,
                userFeedback: state.feedback.map((f) =>
                    f.id === action.payload.id ? action.payload : f
                ),
                error: null,
            };
        case TYPES.UPDATE_FEEDBACK_FAILURE:
            return {
                ...state,
                loading: false,
                error: action.payload,
            };
        case TYPES.DELETE_FEEDBACK_REQUEST:
            return {
                ...state,
                loading: true,
                error: null,
            };
        case TYPES.DELETE_FEEDBACK_SUCCESS:
            return {
                ...state,
                loading: false,
                userFeedback: state.feedback.filter((f) => f.movieId !== action.payload),
                error: null,
            };
        case TYPES.DELETE_FEEDBACK_FAILURE:
            return {
                ...state,
                loading: false,
                error: action.payload,
            };
        default:
            return state;
    }
};

export default feedbackReducer;
