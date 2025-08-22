<!-- ========== Left Sidebar Start ========== -->
<div class="vertical-menu">

    <div data-simplebar class="h-100">

        <!--- Sidemenu -->
        <div id="sidebar-menu">
            <!-- Left Menu Start -->
            <ul class="metismenu list-unstyled" id="side-menu">
                <li class="menu-title"><?php echo lang('Main'); ?></li>

                <li>
                    <a href="<?= site_url('index') ?>" class="waves-effect">
                        <i class="ti-home"></i>
                        <span><?php echo lang('dashboard_sidebar'); ?></span>
                    </a>
                </li>

                <li class="menu-title"><?php echo lang('Contents'); ?></li>

                <li>

                    <a href="javascript: void(0);" class="has-arrow waves-effect">

                        <i class="mdi mdi-movie-open"></i>

                        <span><?php echo lang('movies'); ?></span>

                    </a>

                    <ul class="sub-menu" aria-expanded="false">

                        <li><a class="typcn typcn-plus" href="<?= site_url('add_movie') ?>">
                                <?php echo lang('add_movies'); ?></a></li>

                        <li><a class="typcn typcn-th-list" href="<?= site_url('all_movies') ?>">
                                <?php echo lang('all_movies'); ?></a></li>

                    </ul>

                </li>


                <li>

                    <a href="javascript: void(0);" class="has-arrow waves-effect">

                        <i class="mdi mdi-movie-roll"></i>

                        <span><?php echo lang('web_series'); ?></span>

                    </a>

                    <ul class="sub-menu" aria-expanded="false">

                        <li><a class="typcn typcn-plus" href="<?= site_url('add_web_series') ?>">
                                <?php echo lang('add_web_series'); ?></a></li>

                        <li><a class="typcn typcn-th-list" href="<?= site_url('all_web_series') ?>">
                                <?php echo lang('all_web_series'); ?></a></li>

                    </ul>

                </li>

                <li>

                    <a href="javascript: void(0);" class="has-arrow waves-effect">

                        <i class="mdi mdi-youtube-tv"></i>

                        <span><?php echo lang('live_tv'); ?></span>

                    </a>

                    <ul class="sub-menu" aria-expanded="false">

                        <li><a class="typcn typcn-plus" href="<?= site_url('add_channel') ?>">
                                <?php echo lang('add_channels'); ?></a></li>

                        <li><a class="typcn typcn-th-list" href="<?= site_url('all_channels') ?>">
                                <?php echo lang('all_channels'); ?></a></a></li>

                    </ul>

                </li>

                <li class="menu-title"><?php echo lang('special'); ?></li>
                <li>

                    <a href="<?= site_url('genres') ?>" class="waves-effect">

                        <i class="fab fa-trello"></i>

                        <span><?php echo lang('genres'); ?></span>

                    </a>

                </li>

                <li>

                    <a href="<?= site_url('live_tv_genres') ?>" class="waves-effect">

                        <i class="fas fa-life-ring"></i>

                        <span>Live Tv Genres</span>

                    </a>
                </li>

                <li>

                    <a href="javascript: void(0);" class="has-arrow waves-effect">

                        <i class="fas fa-layer-group"></i>

                        <span>Upcoming Contents</span>

                    </a>

                    <ul class="sub-menu" aria-expanded="false">

                        <li><a class="typcn typcn-plus" href="<?= site_url('add_upcoming_contents') ?>">
                                Add Content</a></li>

                        <li><a class="typcn typcn-th-list" href="<?= site_url('all_upcoming_contents') ?>">
                                All Content</a></li>

                    </ul>

                </li>

                <li>

                    <a href="<?= site_url('content_networks') ?>" class="waves-effect">

                        <i class="fa fa-retweet"></i>

                        <span>Content Networks</span>

                    </a>
                </li>

                <li>

                    <a href="<?= site_url('custom_tags') ?>" class="waves-effect">

                        <i class="fa fa-tag"></i>

                        <span>Custom Tags</span>

                    </a>
                </li>

                <li class="menu-title"><?php echo lang('import'); ?></li>
                <li>

                    <a href="javascript: void(0);" class="has-arrow waves-effect">

                        <i class="fa fa-search"></i>

                        <span><?php echo lang('search'); ?></span>

                    </a>

                    <ul class="sub-menu" aria-expanded="false">

                        <li><a class="mdi mdi-movie-open" href="<?= site_url('search_movie') ?>">
                                <?php echo lang('movies'); ?></a></li>

                        <li><a class="mdi mdi-movie-roll" href="<?= site_url('search_webseries') ?>">
                                <?php echo lang('web_series'); ?></a></li>

                    </ul>

                </li>
                <li>

                    <a href="javascript: void(0);" class="has-arrow waves-effect">

                        <i class="fas fa-magic"></i>

                        <span><?php echo lang('bulk'); ?></span>

                    </a>

                    <ul class="sub-menu" aria-expanded="false">

                        <li><a class="mdi mdi-movie-open" href="<?= site_url('add_bulk_movie') ?>">
                                <?php echo lang('movies'); ?></a></li>

                        <li><a class="mdi mdi-movie-roll" href="<?= site_url('add_bulk_webseries') ?>">
                                <?php echo lang('web_series'); ?></a></li>

                    </ul>

                </li>
                <li class="menu-title"><?php echo lang('SUBSCRIPTION'); ?></li>
                <li>
                    <a href="<?= site_url('coupon_manager') ?>" class="waves-effect">

                        <i class="typcn typcn-ticket"></i>

                        <span><?php echo lang('coupon_manager'); ?></span>

                    </a>

                </li>
                <li>

                    <a href="javascript: void(0);" class="has-arrow waves-effect">

                        <i class="mdi mdi-alert-octagram"></i>

                        <span>subscriptions</span>

                    </a>

                    <ul class="sub-menu" aria-expanded="false">

                        <li><a class="typcn typcn-th-large" href="<?= site_url('sub_plan') ?>">
                                Subscription Plans</a></li>

                        <li><a class="typcn typcn-th-list" href="<?= site_url('sub_request') ?>">
                                Subscription Requests</a></li>

                    </ul>

                </li>
                <li>

                    <a href="javascript: void(0);" class="has-arrow waves-effect">

                        <i class="mdi mdi-credit-card-multiple"></i>

                        <span>Payment Gateway</span>

                    </a>

                    <ul class="sub-menu" aria-expanded="false">

                        <li><a class="mdi mdi-credit-card-outline" href="<?= site_url('payment_gateways') ?>">
                                Payment Gateways</a></li>

                        <li><a class="mdi mdi-wallet-membership" href="<?= site_url('custom_gateways') ?>">
                                Custom Gateways</a></li>

                        <li><a class="mdi mdi-cog" href="<?= site_url('sub_setting') ?>">
                                Settings</a></li>

                    </ul>

                </li>



                <li class="menu-title"> <?php echo lang('push_notification'); ?></li>
                <li>

                    <a href="javascript: void(0);" class="has-arrow waves-effect">

                        <i class="dripicons-broadcast"></i>

                        <span><?php echo lang('notification'); ?></span>

                    </a>

                    <ul class="sub-menu" aria-expanded="false">

                        <li><a class="dripicons-broadcast" href="<?= site_url('announcement') ?>">
                                <?php echo lang('announcement'); ?></a></li>

                        <li><a class="dripicons-rocket" href="<?= site_url('notification_movie') ?>">
                                <?php echo lang('movies'); ?></a></li>
                        <li><a class="dripicons-rocket" href="<?= site_url('notification_web_series') ?>">
                                <?php echo lang('web_series'); ?></a></li>

                        <li><a class="mdi mdi-web-box" href="<?= site_url('notification_web_view') ?>">
                                <?php echo lang('web_view'); ?></a></li>

                        <li><a class="mdi mdi-web" href="<?= site_url('notification_external_browser') ?>">
                                <?php echo lang('external_browser'); ?></a></li>

                        <li><a class="typcn typcn-cog" href="<?= site_url('notification_setting') ?>">
                                <?php echo lang('setting'); ?></a></li>

                    </ul>

                </li>
                <li>
                    <a href="javascript: void(0);" class="has-arrow waves-effect">

                        <i class="mdi mdi-telegram"></i>

                        <span><?php echo lang('telegram_notification'); ?></span>

                    </a>

                    <ul class="sub-menu" aria-expanded="false">

                        <li><a class="dripicons-broadcast" href="<?= site_url('telegram_announcement') ?>">
                                <?php echo lang('announcement'); ?></a></li>

                        <li><a class="typcn typcn-cog" href="<?= site_url('telegram_setting') ?>">
                                <?php echo lang('setting'); ?></a></li>

                    </ul>

                </li>
                <li>
                    <a href="javascript: void(0);" class="has-arrow waves-effect">

                        <i class="mdi mdi-email"></i>

                        <span>MAIL Setting</span>

                    </a>
                    <ul class="sub-menu" aria-expanded="false">
                        <li><a class="mdi mdi-email-mark-as-unread" href="<?= site_url('email_setting') ?>">
                                Email Settings</a></li>
                        <li><a class="mdi mdi-email-edit" href="<?= site_url('email_templates') ?>">
                                Email Templates</a></li>
                    </ul>
                </li>


                <li class="menu-title"><?php echo lang('MISCELLANEOUS'); ?></li>

                <li>
                    <a href="<?= site_url('manage_user') ?>" class="waves-effect">

                        <i class="fas fa-user"></i>

                        <span><?php echo lang('user_manager'); ?></span>

                    </a>
                </li>
                <li>
                    <a href="<?= site_url('request_manager') ?>" class="waves-effect">

                        <i class="typcn typcn-edit"></i>

                        <span><?php echo lang('request_manager'); ?></span>

                    </a>
                </li>
                <li>
                    <a href="<?= site_url('report_manager') ?>" class="waves-effect">

                        <i class="typcn typcn-flag"></i>

                        <span><?php echo lang('report_manager'); ?></span>

                    </a>
                </li>
                <li>
                    <a href="javascript: void(0);" class="has-arrow waves-effect">

                        <i class="dripicons-duplicate"></i>

                        <span><?php echo lang('slider'); ?></span>

                    </a>
                    
                    <ul class="sub-menu" aria-expanded="false">

                        <li><a class="typcn typcn-image" href="<?= site_url('custom_slider') ?>">
                                <?php echo lang('custom_slider'); ?></a></li>

                        <li><a class="typcn typcn-cog" href="<?= site_url('slider_settings') ?>">
                                <?php echo lang('slider_settings'); ?></a></li>

                    </ul>
                </li>

                <li>

                    <a href="javascript: void(0);" class="has-arrow waves-effect">

                        <i class="mdi mdi-cog"></i>

                        <span><?php echo lang('settings'); ?></span>

                    </a>

                    <ul class="sub-menu" aria-expanded="false">
                        <li><a class="typcn typcn-flash" href="<?= site_url('api_setting') ?>">
                                <?php echo lang('API_setting'); ?></a></li>
                        <li><a class="typcn typcn-vendor-android" href="<?= site_url('android_setting') ?>">
                                <?php echo lang('ANDROID_setting'); ?></a></li>
                        <li><a class="typcn typcn-device-desktop" href="<?= site_url('dashboard_setting') ?>">
                                <?php echo lang('dashboard_setting'); ?></a></li>
                        <li><a class="mdi mdi-google-drive" href="<?= site_url('google_drive') ?>">
                                Google Drive</a></li>
                        <li><a class="mdi mdi-currency-usd" href="<?= site_url('ads_setting') ?>">
                                <?php echo lang('ADS_setting'); ?></a></li>
                        <li><a class="mdi mdi-clock-outline" href="<?= site_url('cron_setting') ?>">
                                <?php echo "Cron Setting" ?></a></li>
                        <li><a class="typcn typcn-clipboard" href="<?= site_url('terms_and_conditions') ?>">
                                <?php echo lang('terms_&_condition'); ?>s</a></li>
                        <li><a class="mdi mdi-post-outline" href="<?= site_url('privacy_policy') ?>">
                                <?php echo lang('privacy_policy'); ?></a></li>
                    </ul>
                </li>


                <li>

                    <a href="javascript: void(0);" class="has-arrow waves-effect">

                        <i class="dripicons-device-desktop"></i>

                        <span><?php echo lang('system'); ?></span>

                    </a>

                    <ul class="sub-menu" aria-expanded="false">

                        <li><a href="javascript: void(0);"
                                class="has-arrow mdi mdi-database"><?php echo lang('database'); ?></a>
                            <ul class="sub-menu" aria-expanded="true">
                                <li><a class="mdi mdi-database" href="<?= site_url('db_manager') ?>">Database
                                        Manager</a></li>
                                <li><a class="mdi mdi-database-import" href="<?= site_url('db_import') ?>">Database
                                        Import</a></li>
                                <li><a class="mdi mdi-database-export" href="<?= site_url('db_export') ?>">Database
                                        Export</a></li>
                            </ul>
                        </li>

                        <li><a class="ion ion-md-git-compare" href="<?= site_url('update') ?>">
                                <?php echo lang('update'); ?></a></li>

                    </ul>

                </li>
            </ul>
        </div>
        <!-- Sidebar -->
    </div>
</div>
<!-- Left Sidebar End -->