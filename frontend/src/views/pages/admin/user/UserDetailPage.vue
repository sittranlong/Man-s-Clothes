<script lang="ts">
import {defineComponent} from "vue";
import AdminPage from "@/views/pages/admin/AdminPage.vue";
import UserDetailComponent from "@/components/admin/user/UserDetailComponent.vue";

export default defineComponent({
    name: 'UserDetailPage',
    components: {UserDetailComponent, AdminPage}
})
</script>
<template>
    <admin-page>
        <user-detail-component/>
    </admin-page>
</template>