<script lang="ts">
import {defineComponent, inject} from 'vue'
import {toast} from "vue3-toastify";
import {TourService} from "@/base/service/tour.service";
import {formatDate} from "date-fns";

export default defineComponent({
    name: 'TourComponent',
    setup() {
        const headers = [
            {title: 'STT', value: 'index'},
            {title: 'Tên danh mục', value: 'categoryName'},
            {title: 'Tên tour', value: 'name'},
            {title: 'Ngày khởi hành', value: 'startDate'},
            {title: 'Địa điểm', value: 'location'},
            {title: 'Hành động', value: 'action'}
        ];
        return {
            headers,
            tourService: inject('tourService') as TourService
        }
    },
    data() {
        return {
            tourPage: {
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
            this.tourService.findBySearch(this.searchParams).then((data) => {
                this.tourPage = data;
            }).finally(() => {
                this.isLoading = false;
            });
        },
        viewDetail(item: any) {
            this.$router.push('/admin/tour/' + item.id);
        },
        deleteTour(item: any) {
            if (item) {
                if (confirm('Bạn có chắc chắn muốn xóa danh mục này ?')) {
                    this.tourService.delete(item?.id).then(() => {
                        this.search();
                        toast.success('Xóa danh mục thành công');
                    });
                }
            }
        },
        formatDate
    },
    created() {
        this.search();
    }

})
</script>
<template>
    <div class="text-dark">
        <h1>Danh sách tour</h1>
        <v-text-field
            v-model="searchParams.keyword"
            label="Search"
            prepend-inner-icon="mdi-magnify"
            variant="outlined"
            hide-details
            @change="search"
            single-line
        ></v-text-field>
        <v-btn color="primary" @click="() => $router.push('/admin/tour/new')">Thêm mới</v-btn>
        <v-data-table
            :headers="headers"
            :items="tourPage.content"
            :loading="isLoading"
            :items-per-page="searchParams.size"
            :total-items="tourPage.totalElements"
            :page="searchParams.page"
            :on-update:page="search"
            :on-update:items-per-page="search"
            :search="searchParams.keyword">
            <template v-slot:[`item.index`]="{index}">
                {{ index + 1 }}
            </template>
            <template v-slot:[`item.categoryName`]="{item}">
                {{ item?.category?.name || ''}}
            </template>
            <template v-slot:[`item.startDate`]="{item}">
                {{ formatDate(item?.startDate, 'dd/MM/yyyy hh:MM:ss') }}
            </template>
            <template v-slot:[`item.action`]="{item}">
                <td>
                    <v-icon left @click="viewDetail(item)">mdi-eye</v-icon>
                    <v-icon left @click="deleteTour(item)">mdi-delete</v-icon>
                </td>
            </template>
        </v-data-table>
    </div>
</template>
<style scoped>
</style>