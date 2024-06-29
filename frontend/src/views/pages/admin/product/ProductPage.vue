<script lang="ts">
import {defineComponent} from "vue";
import AdminPage from "@/views/pages/admin/AdminPage.vue";
import ProductComponent from "@/components/admin/product/ProductComponent.vue"

export default defineComponent({
    name: 'ProductPage',
    components: {ProductComponent, AdminPage},
    created() {
        window.scrollTo(0, 0);
    }
})
</script>
<template>
    <admin-page>
        <product-component/>
    </admin-page>
</template>