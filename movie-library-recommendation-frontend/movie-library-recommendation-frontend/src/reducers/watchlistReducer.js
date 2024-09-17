import * as TYPES from '../actions/types';

const initialState = {
    watchlist: [],
    loading: false,
    error: null,
};

const watchlistReducer = (state = initialState, action) => {
    switch (action.type) {
        case TYPES.ADD_TO_WATCHLIST_REQUEST:
            return {
                ...state,
                loading: true,
                error: null,
            };
        case TYPES.ADD_TO_WATCHLIST_SUCCESS:
            return {
                ...state,
                loading: false,
                watchlist: [...state.watchlist, action.payload],
            };
        case TYPES.ADD_TO_WATCHLIST_FAILURE:
            return {
                ...state,
                loading: false,
                error: action.payload,
            };
            case TYPES.DELETE_FROM_WATCHLIST_REQUEST:
            return {
                ...state,
                loading: true,
                error: null,
            };
        case TYPES.DELETE_FROM_WATCHLIST_SUCCESS:
            return {
                ...state,
                loading: false,
            };
        case TYPES.DELETE_FROM_WATCHLIST_FAILURE:
            return {
                ...state,
                loading: false,
                error: action.payload,
            };
        default:
            return state;
    }
};

export default watchlistReducer;