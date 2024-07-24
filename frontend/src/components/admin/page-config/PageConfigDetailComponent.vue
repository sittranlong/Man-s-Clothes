<script lang="ts">
import {defineComponent, inject} from "vue";
import {
    LOCALE_CONSTANT_LIST,
    PAGE_CONFIG_TYPE,
    PAGE_CONFIG_TYPE_LIST,
    PageConfig
} from "@/base/model/page-config.model";
import {PageConfigService} from "@/base/service/page-config.service";
import {toast} from "vue3-toastify";
import {useRoute, useRouter} from "vue-router";
import Editor from "@tinymce/tinymce-vue";
export default defineComponent({
    name: 'PageConfigDetailComponent',
    components: {Editor},
    setup() {
        return {
            PAGE_CONFIG_TYPE: PAGE_CONFIG_TYPE,
            PAGE_CONFIG_TYPE_LIST: PAGE_CONFIG_TYPE_LIST,
            LOCALE_CONSTANT: LOCALE_CONSTANT_LIST,
            pageConfigService: inject('pageConfigService') as PageConfigService,
            router: useRouter(),
            route: useRoute()
        }
    },
    data() {
        return {
            pageConfig: new PageConfig(),
            id: '' as string,
            pageConfigList: [] as PageConfig[],
            showPageConfigPicker: false
        }
    },
    methods: {
        back() {
            this.router.push('/admin/page-config');
        },
        submit() {
            if (this.pageConfig.id) {
                this.pageConfigService.update(this.pageConfig).then(() => {
                    this.router.push('/admin/page-config');
                    toast.success('Cập nhật thành công');
                });
            } else {
                this.pageConfigService.create(this.pageConfig).then(() => {
                    this.router.push('/admin/page-config');
                    toast.success('Thêm mới thành công');
                });
            }
        },
    },
    created() {
        this.id = this.route?.params.id as string;
        this.pageConfigService.get(this.id).then((data: PageConfig) => {
            this.pageConfig = data;
        });
    }
})
</script>
<template>
    <div class="text-dark">
        <div>
            <h1>Chi tiết màu</h1>
        </div>
        <div class="d-flex justify-content-start">
            <v-form class="container row col-10">
                <v-text-field v-model="pageConfig.name" label="Tên cấu hình (*)"></v-text-field>
                <v-text-field v-model="pageConfig.code" label="Mã cấu hình (*)"></v-text-field>
                <v-select v-model="pageConfig.type"
                          item-value="value"
                          item-title="text"
                          :items="PAGE_CONFIG_TYPE_LIST" label="Loại cấu hình (*)"></v-select>
                <div v-if="pageConfig.type === PAGE_CONFIG_TYPE.ABOUT_PAGE.value">
                    <v-label>Giá trị (*)</v-label>
                    <editor v-model="pageConfig.value" label="Giá trị (*)" class="col"
                            api-key="haqb2cfh7d0ungglu7xiok8bqoxo0qm57s5fzmmmxyhiymif"
                            :init="{
                    height: 500,
                    menubar: false,
                    plugins: [
                        'advlist autolink lists link image charmap print preview anchor',
                        'searchreplace visualblocks code fullscreen',
                        'insertdatetime media table paste code help wordcount'
                    ],
                    toolbar:
                        'undo redo | formatselect | bold italic backcolor | \
                        alignleft aligncenter alignright alignjustify | \
                        bullist numlist outdent indent | removeformat | help'
                }"></editor>
                </div>
                <v-text-field v-model="pageConfig.description" label="Mô tả (*)"></v-text-field>
                <v-select v-model="pageConfig.locale"
                          item-value="value"
                          item-title="text"
                          :items="LOCALE_CONSTANT" label="Áp dụng cho ngôn ngữ (*)"></v-select>
                <div class="row">
                    <v-btn class="col" pageConfig="secondary" @click="back">Quay lại</v-btn>
                    <v-btn class="col" pageConfig="primary" @click="submit">Lưu</v-btn>
                </div>
            </v-form>
        </div>
    </div>
</template>
