<script lang="ts">
import {defineComponent, inject} from 'vue'
import {ReviewService} from "@/base/service/review.service";
import {useRouter} from "vue-router";
import {toast} from "vue3-toastify";

export default defineComponent({
    name: 'ReviewComponent',
    setup() {
        const headers = [
            {title: 'STT', value: 'index'},
            {title: 'Tên sản phẩm', value: 'product.name'},
            {title: 'Tên người đánh giá', value: 'user.fullName'},
            {title: 'Đánh giá', value: 'rating'},
            {title: 'Nội dung', value: 'content'},
            {title: 'Hành động', value: 'action', sortable: false},
        ];
        return {
            headers,
            reviewService: inject('reviewService') as ReviewService,
            router: useRouter()
        }
    },
    data() {
        return {
            reviewPage: {
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
            this.reviewService.findBySearch(this.searchParams).then((data) => {
                this.reviewPage = data;
            }).finally(() => {
                this.isLoading = false;
            });
        },
        viewDetail(item: any) {
            this.router.push('/admin/review/' + item.id);
        },
        approveReview(item: any) {
            if (item) {
                if (confirm('Bạn có chắc chắn muốn duyệt đánh giá này ?')) {
                    this.reviewService.approve(item?.id).then(() => {
                        this.search();
                        toast.success('Duyệt đánh giá thành công');
                    });
                }
            }
        },
    },
    created() {
        this.search();
    }

})
</script>
<template>
    <div class="text-dark">
        <h1>Danh sách kích cỡ</h1>
        <v-text-field
            v-model="searchParams.keyword"
            label="Search"
            prepend-inner-icon="mdi-magnify"
            variant="outlined"
            hide-details
            @change="search"
            single-line
        ></v-text-field>
        <v-btn color="primary" @click="() => router.push('/admin/review/new')">Thêm mới</v-btn>
        <v-data-table
            :headers="headers"
            :items="reviewPage.content"
            :loading="isLoading"
            :items-per-page="searchParams.size"
            :on-update:items-per-page="search"
            :page="searchParams.page"
            :on-update:page="search"
            :search="searchParams.keyword"
            :total-items="reviewPage.totalElements">
            <template v-slot:[`item.index`]="{index}">
                <td>{{ index + 1 }}</td>
            </template>
            <template v-slot:[`item.action`]="{item}">
                <td>
                    <v-icon v-if="item.status === 'PENDING'" title="Duyệt đánh giá" left @click="approveReview(item)">mdi-check</v-icon>
                </td>
            </template>
        </v-data-table>
    </div>
</template>
<style scoped>
</style>