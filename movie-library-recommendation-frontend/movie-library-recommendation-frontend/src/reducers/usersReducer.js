import * as TYPES from '../actions/types';

const initialState = {
    loading: true,
    users: [],
    error: null,
};

const usersReducer = (state = initialState, action) => {
    switch (action.type) {
        case TYPES.FETCH_USERS_SEARCH_REQUEST:
            return {
                ...state,
                loading: true,
            };
        case TYPES.FETCH_USERS_SEARCH_SUCCESS:
            return {
                ...state,
                loading: false,
                users: action.payload,
                error: null,
            };
        case TYPES.FETCH_USERS_SEARCH_FAILURE:
            return {
                ...state,
                loading: false,
                error: action.payload,
            };
        default:
            return state;
    }
};

export default usersReducer;