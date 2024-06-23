<script lang="ts">
import {defineComponent} from "vue";
import AdminPage from "@/views/pages/admin/AdminPage.vue";
import OrderComponent from "@/components/admin/order/OrderComponent.vue";

export default defineComponent({
    name: 'OrderPage',
    components: {OrderComponent, AdminPage}
})
</script>
<template>
    <admin-page>
        <order-component/>
    </admin-page>
</template>