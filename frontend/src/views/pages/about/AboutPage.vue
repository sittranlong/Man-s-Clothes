<template>
    <div>
        <header-component/>
        <div class="page-heading about-heading header-text">
            <div class="container">
                <div class="row">
                    <div class="col-md-12">
                    </div>
                </div>
            </div>
        </div>
        <div class="best-features about-features">
            <div class="container">
                <div class="row">
                    <div class="col-md-12">
                        <div class="section-heading">
                            <h2>Our Background</h2>
                        </div>
                    </div>
                    <div class="col-md-6 p-1">
                        <div class="right-image">
                            <img src="../../../assets/images/feature-image.jpg" alt=""/>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="left-content">
                            <div v-html="pageConfig.value" :title="pageConfig.description"></div>
                            <ul class="social-icons">
                                <li>
                                    <a target="_blank">
                                        <i title="facebook link" class="fa fa-facebook"></i>
                                    </a>
                                </li>
                                <li>
                                    <a href="#">
                                        <i title="twitter link" class="fa fa-twitter"></i>
                                    </a>
                                </li>
                                <li>
                                    <a target="_blank" href="https://gitgub.com/pudo58">
                                        <i title="github link" class="fa fa-github"></i>
                                    </a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="team-members">
            <div class="container">
                <div class="row">
                    <div class="col-md-12">
                        <div class="section-heading">
                            <h2>Member</h2>
                        </div>
                    </div>
                    <div class="col-md-4" v-for="(item,index) in teamManagerList" :key="index">
                        <div class="team-member">
                            <div class="thumb-container">
                                <img :src="item.image" alt=""/>
                                <div class="hover-effect">
                                    <div class="hover-content">
                                        <ul class="social-icons">
                                            <li><a :href="item.facebook"><i class="fa fa-facebook"></i></a></li>
                                            <li><a href=""><i class="fa fa-twitter"></i></a></li>
                                            <li><a :href="item.github"><i class="fa fa-github"></i></a></li>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                            <div class="down-content">
                                <h4>{{ item.name }}</h4>
                                <span>{{ item.role }}</span>
                                <p class="limit-line-2">{{ item.description }}</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <our-customer-component/>
        <footer-component/>
        <loading-component v-if="loading"/>
    </div>
</template>
<script lang="ts">
import {defineComponent, inject} from "vue";
import HeaderComponent from "@/components/header/HeaderComponent.vue";
import {TeamManagerModel} from "@/base/model/team-manager.model";
import FooterComponent from "@/components/footer/FooterComponent.vue";
import OurCustomerComponent from "@/components/our-customer/OurCustomerComponent.vue";
import LoadingComponent from "@/components/loading/LoadingComponent.vue";
import {PageConfigService} from "@/base/service/page-config.service";

export default defineComponent({
    name: 'AboutPage',
    components: {
        HeaderComponent,
        FooterComponent,
        OurCustomerComponent,
        LoadingComponent
    },
    setup() {
        const teamManagerList = [
            {
                name: 'pudo58',
                role: 'CO-Founder',
                image: 'src/assets/images/team_01.jpg',
                github: 'https://github.com/pudo58',
                facebook: 'https://www.facebook.com/profile.php?id=100010911062900',
                description: 'Software Engineer',
            },
            {
                name: 'Karry Pitcher',
                role: 'Product Expert',
                image: 'src/assets/images/team_02.jpg',
                github: 'https://github.com/pudo58',
                facebook: 'https://www.facebook.com/profile.php?id=100010911062900',
                description: 'Software Engineer',
            },
            {
                name: 'Michael Soft',
                role: 'Chief Marketing',
                image: 'src/assets/images/team_03.jpg',
                github: 'https://github.com/pudo58',
                facebook: 'https://www.facebook.com/profile.php?id=100010911062900',
                description: 'Software Engineer',
            },
            {
                name: 'Mary Cool',
                role: 'Product Specialist',
                image: 'src/assets/images/team_04.jpg',
                github: 'https://github.com/pudo58',
                facebook: 'https://www.facebook.com/profile.php?id=100010911062900',
                description: 'Software Engineer',
            },
            {
                name: 'George Walker',
                role: 'Product Photographer',
                image: 'src/assets/images/team_05.jpg',
                github: 'https://github.com/pudo58',
                facebook: 'https://www.facebook.com/profile.php?id=100010911062900',
                description: 'Software Engineer',
            },
            {
                name: 'Kate Town',
                role: 'General Manager',
                image: 'src/assets/images/team_06.jpg',
                github: 'https://github.com/pudo58',
                facebook: 'https://www.facebook.com/profile.php?id=100010911062900',
                description: 'Software Engineer',
            },
        ] as TeamManagerModel[];
        return {
            teamManagerList,
            pageConfigService: inject('pageConfigService') as PageConfigService
        }
    },
    data() {
        return {
            loading: false,
            pageConfig : {
                value : '',
                description : ''
            }
        }
    }, methods: {
        async created() {
            this.loading = true;
            await this.pageConfigService.findByCode({
                code: 'about_page'
            }).then(res => {
                this.pageConfig = res;
            }).catch(err => {
                console.log(err);
            }).finally(() => {
                this.loading = false;
            });
        }
    },
    created() {
        this.created();
    }
})
</script>