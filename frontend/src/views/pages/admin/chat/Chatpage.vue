<script lang="ts">
import {defineComponent} from "vue";
import AdminPage from "@/views/pages/admin/AdminPage.vue";
import AdminChatComponent from "@/components/admin/chat/AdminChatComponent.vue";

export default defineComponent({
    name: 'ChatPage',
    components: {AdminChatComponent, AdminPage}
})
</script>
<template>
    <admin-page>
        <admin-chat-component/>
    </admin-page>
</template>