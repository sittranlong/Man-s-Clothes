<script lang="ts">
import {defineComponent, inject} from "vue";
import {Bar} from 'vue-chartjs'
import {BarElement, CategoryScale, Chart as ChartJS, Legend, LinearScale, Title, Tooltip} from 'chart.js'
import type {OrderService} from "@/base/service/order.service";

ChartJS.register(Title, Tooltip, Legend, BarElement, CategoryScale, LinearScale)

export default defineComponent({
    components: {
        Bar
    }, setup() {
        return {
            orderService: inject('orderService') as OrderService
        }
    },
    data() {
        return {
            chartData: {
                labels: ['Tour đã thanh toán', 'Tour đã hủy', 'Tổng doanh thu'],
                datasets: [
                    {
                        label: 'Doanh thu',
                        backgroundColor: ['#f87979', '#f87979', '#f87979'],
                        data: [1,4,33232111]
                    }]
            },
            chartOptions: {
                responsive: true
            }
        }
    }, created() {
        this.orderService.chart().then((data) => {
            if (data) {
                this.chartData.datasets[0].data = data
            }
        });
    }
})
</script>

<template>
    <Bar
        id="my-chart-id"
        :options="chartOptions"
        :data="chartData"
    />
</template>