<template>
    <div>
        <ul class="pages">
            <li>
                <a role="button" @click="handlePageChange(page - 1)" :class="{'disabled' : first}">
                    <i class="fa fa-angle-double-left"></i>
                </a>
            </li>
            <template v-for="pageNumber in Array.from({ length: totalPage }, (_, index) => index + 1)" :key="pageNumber">
                <li v-if="showPage(pageNumber)" :class="{ 'active': page === pageNumber -1 }">
                    <a role="button" @click="handlePageChange(pageNumber - 1)">
                        {{ pageNumber }}
                    </a>
                </li>
                <li v-else-if="isEllipsis(pageNumber)" key="ellipsis">
                    <span>&hellip;</span>
                </li>
            </template>
            <li>
                <a role="button" @click="handlePageChange(page + 1)" :class="{'disabled' : last}">
                    <i class="fa fa-angle-double-right"></i>
                </a>
            </li>
        </ul>
    </div>
</template>

<script lang="ts">
import { defineComponent, ref, watch } from "vue";

export default defineComponent({
    name: 'PageComponent',
    props: {
        page: {
            type: Number,
            default: 0
        },
        totalPage: {
            type: Number,
            default: 0
        },
        pageChange: {
            type: Function,
            required: true,
            default: () => {}
        },
        first: {
            type: Boolean,
            default: false
        },
        last: {
            type: Boolean,
            default: false
        }
    },
    setup(props) {
        const page = ref(props.page);
        const totalPage = ref(props.totalPage);

        const handlePageChange = (pageNumber: number) => {
            if (pageNumber >= 0 && pageNumber < totalPage.value) {
                page.value = pageNumber;
                props.pageChange(pageNumber);
            }
        };

        watch(() => props.page, (newVal) => {
            page.value = newVal;
        });

        const showPage = (pageNumber: number) => {
            const currentIndex = pageNumber - 1;
            return (
                (currentIndex < 5 && page.value < 5) ||
                (currentIndex >= page.value - 2 && currentIndex <= page.value + 2) ||
                (currentIndex > totalPage.value - 5)
            );
        };

        const isEllipsis = (pageNumber: number) => {
            const currentIndex = pageNumber - 1;
            return !(
                (currentIndex < 5 && page.value < 5) ||
                (currentIndex >= page.value - 2 && currentIndex <= page.value + 2) ||
                (currentIndex > totalPage.value - 5)
            );
        };

        return {
            handlePageChange,
            showPage,
            isEllipsis
        };
    }
});
</script>

<style scoped lang="css">
@import "style.css";
/* CSS scoped để áp dụng cho component */
</style>
