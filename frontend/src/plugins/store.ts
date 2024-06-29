import { createStore } from 'vuex'
import createPersistedState from "vuex-persistedstate";

const store = createStore({
    state: {
        user: null
    },
    mutations: {
        SET_USER(state : any, user: any) {
            state.user = user
        },
        CLEAR_USER(state: any) {
            state.user = null
        }
    },
    actions: {
        login({ commit }: any, user: any) {
            commit('SET_USER', user)
        },
        logout({ commit }: any) {
            commit('CLEAR_USER')
        }
    },
    getters: {
        isAuthenticated(state: any) {
            return !!state.user
        },
        getUser(state: any) {
            return state.user
        },
        getToken(state: any) {
            return 'Bearer ' + state.user?.token
        },
        isAdmin(state: any) {
            return state.user?.roleList?.includes('ROLE_ADMIN')
        }
    },
    plugins: [createPersistedState()]
})

export default store