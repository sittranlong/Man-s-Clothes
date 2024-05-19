<script lang="tsx">
import {defineComponent} from "vue";
import HeaderComponent from "@/components/header/HeaderComponent.vue";
import {TeamManagerModel} from "@/base/model/team-manager.model";
import FooterComponent from "@/components/footer/FooterComponent.vue";
import OurCustomerComponent from "@/components/our-customer/OurCustomerComponent.vue";
import LoadingComponent from "@/components/loading/LoadingComponent.vue";

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
        const headerImage = (
            <div class="page-heading about-heading header-text">
                <div class="container">
                    <div class="row">
                        <div class="col-md-12">
                            <div class="text-content">
                                <h4>about us</h4>
                                <h2>our company</h2>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        );
        const aboutFeature = (
            <div class="best-features about-features">
                <div class="container">
                    <div class="row">
                        <div class="col-md-12">
                            <div class="section-heading">
                                <h2>Our Background</h2>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="right-image">
                                <img src="../../../assets/images/feature-image.jpg" alt=""/>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="left-content">
                                <h4>Im pudo58</h4>
                                <p>
                                    I'm pudo58. I have more than 2 years of experience working as a software engineer.
                                    I like to learn new technologies and apply them to my work. I am a hard-working
                                    person,
                                    always trying to improve myself and learn new things. I am a team player and I like
                                    to work in a
                                    team.
                                </p>
                                <ul class="social-icons">
                                    <li>
                                        <a target="_blank"
                                           href="https://www.facebook.com/profile.php?id=100010911062900">
                                            <i title="facebook link" class="fa fa-facebook"></i>
                                        </a>
                                    </li>
                                    <li>
                                        <a href="#">
                                            <i title="twitter link"  class="fa fa-twitter"></i>
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
        );
        const teamMember = (
            <div class="team-members">
                <div class="container">
                    <div class="row">
                        <div class="col-md-12">
                            <div class="section-heading">
                                <h2>Member</h2>
                            </div>
                        </div>
                        {teamManagerList.map((item) => (
                            <div class="col-md-4">
                                <div class="team-member">
                                    <div class="thumb-container">
                                        <img src={item?.image} alt=""/>
                                        <div class="hover-effect">
                                            <div class="hover-content">
                                                <ul class="social-icons">
                                                    <li><a href={item?.facebook}><i class="fa fa-facebook"></i></a></li>
                                                    <li><a href=""><i class="fa fa-twitter"></i></a></li>
                                                    <li><a href={item?.github}><i class="fa fa-github"></i></a></li>
                                                </ul>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="down-content">
                                        <h4>{item.name}</h4>
                                        <span>{item.role}</span>
                                        <p class="limit-line-2">{item.description}</p>
                                    </div>
                                </div>
                            </div>
                        ))}
                    </div>
                </div>
            </div>
        );
        const loadingComponent = <LoadingComponent loading={true}/>
        return () => (
            <div>
                {loadingComponent}
                <header-component/>
                {headerImage}
                {aboutFeature}
                {teamMember}
                <our-customer-component/>
                <footer-component/>
            </div>
        )
    }
})
</script>