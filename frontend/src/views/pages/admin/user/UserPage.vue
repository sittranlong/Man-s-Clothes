<script lang="ts">
import {defineComponent} from "vue";
import AdminPage from "@/views/pages/admin/AdminPage.vue";
import UserComponent from "@/components/admin/user/UserComponent.vue";
export default defineComponent({
    name : 'UserPage',
    components : {UserComponent, AdminPage}
})
</script>
<template>
    <admin-page>
        <user-component/>
    </admin-page>
</template>