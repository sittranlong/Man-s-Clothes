<script lang="ts">
import {defineComponent} from "vue";
import AdminPage from "@/views/pages/admin/AdminPage.vue";
import OrderDetailComponent from "@/components/admin/order/OrderDetailComponent.vue";

export default defineComponent({
    name: 'OrderPage',
    components: {OrderDetailComponent, AdminPage}
})
</script>
<template>
    <admin-page>
        <order-detail-component/>
    </admin-page>
</template>