<script lang="ts">
import {defineComponent, inject} from 'vue'
import {UserService} from "@/base/service/user.service";
import {toast} from "vue3-toastify";
import {useRouter} from "vue-router";

export default defineComponent({
    name: 'UserComponent',
    setup() {
        const headers = [
            {title: 'STT', value: 'index'},
            {title: 'Ảnh đại diện', value: 'avatar'},
            {title: 'Username', value: 'username'},
            {title: 'Email', value: 'email'},
            {title: 'Địa chỉ', value: 'address'},
            {title: 'Hành động', value: 'action'}
        ];
        return {
            headers,
            userService: inject('userService') as UserService,
            router : useRouter()
        }
    },
    data() {
        return {
            userPage: {
                content: [],
                totalPages: 0,
                totalElements: 0,
                last: true,
                size: 0,
            } as any,
            searchParams: {
                page: 0,
                size: 10,
                keyword: '',
            },
            isLoading: false,
        }
    },
    methods: {
        async search() {
            this.isLoading = true;
            this.userService.findBySearch(this.searchParams).then((data) => {
                this.userPage = data;
            }).finally(() => {
                this.isLoading = false;
            });
        },
        viewDetail(item: any) {
            this.router.push('/admin/user/' + item.id);
        },
        deleteUser(item: any) {
            if (item) {
                if (confirm('Bạn có chắc chắn muốn xóa người dùng này ?')) {
                    this.userService.delete(item?.id).then(() => {
                        this.search();
                        toast.success('Xóa người dùng thành công');
                    });
                }
            }
        }
    },
    created() {
        this.search();
    }

})
</script>
<template>
    <div class="text-dark">
        <h1>Danh sách người dùng</h1>
        <v-text-field
            v-model="searchParams.keyword"
            label="Search"
            prepend-inner-icon="mdi-magnify"
            variant="outlined"
            hide-details
            @change="search"
            single-line
        ></v-text-field>
        <v-data-table
            :headers="headers"
            :items="userPage.content"
            :loading="isLoading"
            :items-per-page="searchParams.size"
            :total-items="userPage.totalElements"
            :page="searchParams.page"
            :on-update:page="search"
            :on-update:items-per-page="search"
            :search="searchParams.keyword"
            @update:page="($event) => {searchParams.page = $event;search}"
            @update:items-per-page="($event) => {searchParams.size = $event;search}">
            <template v-slot:[`item.index`]="{index}">
                {{ index + 1 }}
            </template>
            <template v-slot:[`item.username`]="{item}">
                {{ item?.username || '' }}
            </template>
            <template v-slot:[`item.avatar`]="{item}">
                <v-avatar :image="'data:image/png;base64,' + item?.avatar" size="80" class="border-dark"></v-avatar>
            </template>
            <template v-slot:[`item.action`]="{item}">
               <td>
                   <v-icon left @click="viewDetail(item)">mdi-eye</v-icon>
                   <v-icon left @click="deleteUser(item)">mdi-delete</v-icon>
               </td>
            </template>
        </v-data-table>
    </div>
</template>
<style scoped>
</style>