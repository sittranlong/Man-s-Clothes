<script lang="ts">
import {defineComponent, inject} from "vue";
import type {UserService} from "@/base/service/user.service";
import {User} from "@/base/model/user.model";

export default defineComponent({
    name: 'UserDetailComponent',
    setup() {
        return {
            userService: inject('userService') as UserService
        }
    },
    data() {
        return {
            user: new User(),
            id: '' as string
        }
    },
    methods: {
        back () {
            this.$router.push('/admin/user');
        }
    },
    created() {
        this.id = this.$route.params.id as string;
        this.userService.get(this.id).then((data: User) => {
            this.user = data;
        });
    }
})
</script>
<template>
    <div class="text-dark text-center">
        <div>
            <h1>Chi tiết người dùng</h1>
        </div>
       <div class="d-flex justify-content-center">
           <v-form class="container row col-6">
               <v-avatar v-if="user.avatar" size="100" :image="'data:image/png;base64,' + user?.avatar"></v-avatar>
               <v-text-field v-model="user.username" label="Username" :readonly="true"></v-text-field>
               <v-text-field v-model="user.email" label="Email" :readonly="true"></v-text-field>
               <v-text-field v-model="user.address" label="Địa chỉ" :readonly="true"></v-text-field>
               <v-btn color="primary" @click="back">Quay lại</v-btn>
           </v-form>
       </div>

    </div>
</template>
