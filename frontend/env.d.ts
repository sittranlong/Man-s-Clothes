/// <reference types="vite/client" />
module '*.vue' {
    import { defineComponent } from 'vue'
    const component: ReturnType<typeof defineComponent>
    export default component
}
import { ComponentCustomProperties } from 'vue'
import { Store } from 'vuex'
declare module "vuex" {
    export * from "vuex/types/index.d.ts";
    export * from "vuex/types/helpers.d.ts";
    export * from "vuex/types/logger.d.ts";
    export * from "vuex/types/vue.d.ts";
}
declare module '@vue/runtime-core' {
    // Declare your own store states.
    interface State {
        count: number
    }

    interface ComponentCustomProperties {
        $store: Store<State>
    }
}