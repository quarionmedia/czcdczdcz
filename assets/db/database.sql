-- phpMyAdmin SQL Dump
-- version 6.0.0-dev+20250304.3b72ad4095
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Mar 12, 2025 at 02:21 PM
-- Server version: 9.2.0
-- PHP Version: 8.3.17

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `dooo`
--

-- --------------------------------------------------------

--
-- Table structure for table `ci_sessions`
--

CREATE TABLE `ci_sessions` (
  `id` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `ip_address` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `timestamp` int UNSIGNED NOT NULL DEFAULT '0',
  `data` blob NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `comments`
--

CREATE TABLE `comments` (
  `id` int NOT NULL,
  `user_id` int NOT NULL,
  `content_id` int NOT NULL,
  `content_type` int NOT NULL COMMENT '1=Movie, 2=WebSeries',
  `comment` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `config`
--

CREATE TABLE `config` (
  `id` int NOT NULL,
  `name` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `logo` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `package_name` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `api_key` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `license_code` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `license_user` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `license_access_token` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `license_token_type` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `login_mandatory` int NOT NULL COMMENT '0=No, 1=Yes',
  `maintenance` int NOT NULL COMMENT '0=No, 1=Yes',
  `image_slider_type` int NOT NULL COMMENT '0=Movie, 1=Web Series, 2=Custom, 3=Disable',
  `movie_image_slider_max_visible` int NOT NULL DEFAULT '5',
  `webseries_image_slider_max_visible` int NOT NULL DEFAULT '5',
  `onesignal_api_key` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `onesignal_appid` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `ad_type` int NOT NULL DEFAULT '0' COMMENT '0=No Ads, 1 =AdMob, 2=Startapp, 3=Facebook, 4=AdColony, 5=UnityAds, 6=CustomAds',
  `Admob_Publisher_ID` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `Admob_APP_ID` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `adMob_Native` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `adMob_Banner` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `adMob_Interstitial` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `adMob_AppOpenAd` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `StartApp_App_ID` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `facebook_app_id` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `facebook_banner_ads_placement_id` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `facebook_interstitial_ads_placement_id` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `Latest_APK_Version_Name` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `Latest_APK_Version_Code` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `APK_File_URL` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `Whats_new_on_latest_APK` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `Update_Skipable` int NOT NULL DEFAULT '0' COMMENT '0=No, 1=Yes',
  `Update_Type` int NOT NULL DEFAULT '0' COMMENT '0=In App, 1 = External Brawser, 2 = Playstore',
  `googleplayAppUpdateType` int NOT NULL DEFAULT '0' COMMENT '0 = FLEXIBLE, 1 = IMMEDIATE',
  `Contact_Email` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `SMTP_Host` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `SMTP_Username` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `SMTP_Password` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `SMTP_Port` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `SMTP_crypto` enum('none','ssl','tls') CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `Dashboard_Version` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `Dashboard_Version_Code` int NOT NULL,
  `shuffle_contents` int NOT NULL DEFAULT '0' COMMENT '0=No, 1=Yes',
  `Home_Rand_Max_Movie_Show` int NOT NULL DEFAULT '0',
  `Home_Rand_Max_Series_Show` int NOT NULL DEFAULT '0',
  `Home_Recent_Max_Movie_Show` int NOT NULL DEFAULT '0',
  `Home_Recent_Max_Series_Show` int NOT NULL DEFAULT '0',
  `Show_Message` int NOT NULL DEFAULT '0' COMMENT '0=No, 1=Yes',
  `message_animation_url` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `Message_Title` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `Message` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `all_live_tv_type` int NOT NULL DEFAULT '0' COMMENT '0=Default, 1=Free, 2=Paid',
  `all_movies_type` int NOT NULL DEFAULT '0' COMMENT '0=Default, 1=Free, 2=Paid',
  `all_series_type` int NOT NULL DEFAULT '0' COMMENT '0=Default, 1=Free, 2=Paid',
  `LiveTV_Visiable_in_Home` int NOT NULL DEFAULT '1' COMMENT '0=No, 1=Yes',
  `TermsAndConditions` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `PrivecyPolicy` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `tmdb_language` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `admin_panel_language` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `genre_visible_in_home` int NOT NULL DEFAULT '1' COMMENT '0=No, 1=Yes',
  `AdColony_app_id` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `AdColony_banner_zone_id` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `AdColony_interstitial_zone_id` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `unity_game_id` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `unity_banner_id` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `unity_interstitial_id` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `custom_banner_url` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `custom_banner_click_url_type` int NOT NULL DEFAULT '0' COMMENT '0=nothing 1=External Brawser 2=Internal Brawser',
  `custom_banner_click_url` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `custom_interstitial_url` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `custom_interstitial_click_url_type` int NOT NULL DEFAULT '0' COMMENT '0=nothing 1=External Brawser 2=Internal Brawser',
  `custom_interstitial_click_url` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `applovin_sdk_key` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `applovin_apiKey` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `applovin_Banner_ID` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `applovin_Interstitial_ID` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `ironSource_app_key` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `movie_comments` int NOT NULL COMMENT '0=Off, 1=On',
  `webseries_comments` int NOT NULL COMMENT '0=Off, 1=On',
  `google_login` int NOT NULL COMMENT '0=Disabled, 1=Enabled',
  `onscreen_effect` int NOT NULL COMMENT '0=Nothing, 1=Snow',
  `razorpay_status` int NOT NULL COMMENT '0=Disabled, 1=Enabled',
  `razorpay_key_id` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `razorpay_key_secret` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `paypal_status` int NOT NULL COMMENT '0=Disabled, 1=Enabled',
  `paypal_type` int NOT NULL,
  `paypal_clint_id` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `paypal_secret_key` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `content_item_type` int NOT NULL DEFAULT '0' COMMENT '0=Default, 1=v2',
  `live_tv_content_item_type` int NOT NULL DEFAULT '0' COMMENT '0=Default, 1=v2',
  `webSeriesEpisodeitemType` int NOT NULL DEFAULT '0' COMMENT '0=Default, 1=v2',
  `telegram_token` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `telegram_chat_id` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `splash_screen_type` int NOT NULL DEFAULT '0' COMMENT '	0=Default, 1=Image, 2=Lottie, 3=Custom',
  `splash_bg_color` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `splash_image_url` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `splash_lottie_url` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `cron_key` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `cron_status` int NOT NULL DEFAULT '0' COMMENT '0=Disabled, 1=Enabled',
  `auto_notification_status` int NOT NULL DEFAULT '0' COMMENT '	0=Disabled, 1=Enabled',
  `auto_notification_schedule` int NOT NULL DEFAULT '0',
  `db_backup_status` int NOT NULL DEFAULT '0' COMMENT '	0=Disabled, 1=Enabled',
  `db_backup_schedule` int NOT NULL DEFAULT '0',
  `safeModeVersions` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `safeMode` int NOT NULL DEFAULT '0',
  `primeryThemeColor` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `blocked_regions` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `pinLockStatus` int NOT NULL,
  `pinLockPin` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `flutterwave_status` int NOT NULL,
  `flutterwave_public_key` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `flutterwave_secret_key` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `flutterwave_encryption_key` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `onboarding_status` int NOT NULL,
  `movieDefaultStreamLinkType` int NOT NULL,
  `movieDefaultStreamLinkStatus` int NOT NULL,
  `live_tv_genre_visible_in_home` int NOT NULL DEFAULT '0',
  `login_otp_status` int NOT NULL,
  `signup_otp_status` int NOT NULL,
  `force_single_device` int NOT NULL DEFAULT '0',
  `payment_gateway_type` int NOT NULL DEFAULT '0' COMMENT '0=Payment Gateways, 1=Custom Gateways',
  `home_bottom_floting_menu_status` int NOT NULL DEFAULT '0',
  `uddoktapay_status` int NOT NULL,
  `uddoktapay_api_key` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `uddoktapay_base_url` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `bKash_status` int NOT NULL,
  `bKash_app_key` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `bKash_app_secret` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `bKash_username` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `bKash_password` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `bKash_payment_type` int NOT NULL COMMENT '0=Sandbox, 1=Live',
  `embed_error_code` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `download_manager` int NOT NULL DEFAULT '0' COMMENT '0=dooo download manager, 1=adm download manager',
  `player_intro` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `image_proxy_status` int NOT NULL DEFAULT '0',
  `image_storage_provider` int NOT NULL COMMENT '0=default, 1=imgbb',
  `imgbb_api_key` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `wortise_app_key` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `wortise_banner_ad_id` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `wortise_interstitial_ad_id` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `wortise_native_ad_id` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `wortise_appopen_ad_id` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `google_play_billing_status` int NOT NULL,
  `welcome_mail_status` int NOT NULL,
  `bkash_exchange_rate` int NOT NULL,
  `uddoktapay_exchange_rate` int NOT NULL,
  `vidhide_api_key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci DEFAULT NULL,
  `stripe_status` int NOT NULL DEFAULT '0',
  `stripe_publishable_key` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `stripe_secret_key` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `coingate_status` int NOT NULL,
  `coingate_payment_environment` int NOT NULL COMMENT '0=sandbox, 1=live',
  `coingate_auth_token` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_520_ci;

-- --------------------------------------------------------

--
-- Table structure for table `content_network_log`
--

CREATE TABLE `content_network_log` (
  `id` int NOT NULL,
  `content_id` int NOT NULL,
  `network_id` int NOT NULL,
  `content_type` int NOT NULL COMMENT '1=Movie, 2=WebSeries'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `coupon`
--

CREATE TABLE `coupon` (
  `id` int NOT NULL,
  `name` text NOT NULL,
  `coupon_code` text NOT NULL,
  `time` int NOT NULL COMMENT 'Days',
  `amount` int NOT NULL,
  `subscription_type` int NOT NULL DEFAULT '0' COMMENT '1=Remove Ads, 2=Play Premium, 3=Download Premium	',
  `status` int NOT NULL COMMENT '0=Expired, 1=Valid',
  `max_use` int NOT NULL DEFAULT '1',
  `used` int NOT NULL DEFAULT '0',
  `used_by` text NOT NULL,
  `expire_date` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- --------------------------------------------------------

--
-- Table structure for table `custom_payment_requests`
--

CREATE TABLE `custom_payment_requests` (
  `id` int NOT NULL,
  `user_id` int NOT NULL,
  `payment_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `payment_details` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `subscription_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `subscription_type` int NOT NULL,
  `subscription_time` int NOT NULL,
  `subscription_amount` int NOT NULL,
  `subscription_currency` int NOT NULL COMMENT '0=INR,1=USD',
  `uploaded_image` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `request_status` int NOT NULL DEFAULT '0' COMMENT '0=pending, 1=approved, 2=declined'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `custom_payment_type`
--

CREATE TABLE `custom_payment_type` (
  `id` int NOT NULL,
  `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `payment_details` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `status` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `custom_tags`
--

CREATE TABLE `custom_tags` (
  `id` int NOT NULL,
  `name` text NOT NULL,
  `background_color` text NOT NULL,
  `text_color` text NOT NULL,
  `created_at` int NOT NULL,
  `updated_at` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `custom_tag_log`
--

CREATE TABLE `custom_tag_log` (
  `id` int NOT NULL,
  `custom_tags_id` int NOT NULL,
  `content_id` int NOT NULL,
  `content_type` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `devices`
--

CREATE TABLE `devices` (
  `id` int NOT NULL,
  `device` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `devices_log`
--

CREATE TABLE `devices_log` (
  `id` int NOT NULL,
  `device_id` int NOT NULL,
  `open_date` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `open_time` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `disposable_emails`
--

CREATE TABLE `disposable_emails` (
  `id` int NOT NULL,
  `emails` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `episode_download_links`
--

CREATE TABLE `episode_download_links` (
  `id` int NOT NULL,
  `name` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `size` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `quality` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `link_order` int NOT NULL,
  `episode_id` int NOT NULL,
  `url` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `type` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `download_type` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `status` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `episode_play_links`
--

CREATE TABLE `episode_play_links` (
  `id` int NOT NULL,
  `name` text NOT NULL,
  `size` text NOT NULL,
  `quality` text NOT NULL,
  `link_order` int NOT NULL,
  `episode_id` int NOT NULL,
  `url` text NOT NULL,
  `type` text NOT NULL,
  `status` int NOT NULL COMMENT '	0=Not Released, 1=Released',
  `skip_available` int NOT NULL COMMENT '	0=No, 1=Yes',
  `intro_start` text NOT NULL,
  `intro_end` text NOT NULL,
  `end_credits_marker` text NOT NULL,
  `link_type` int NOT NULL COMMENT '0=NotPremium, 1=Premium',
  `drm_uuid` text NOT NULL COMMENT 'WIDEVINE,PLAYREADY,CLEARKEY	',
  `drm_license_uri` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `favourite`
--

CREATE TABLE `favourite` (
  `id` int NOT NULL,
  `user_id` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `content_type` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `content_id` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_520_ci;

-- --------------------------------------------------------

--
-- Table structure for table `genres`
--

CREATE TABLE `genres` (
  `id` int NOT NULL,
  `name` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `icon` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `description` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `featured` int NOT NULL COMMENT '0=NotFeatured, 1=Featured',
  `status` int NOT NULL COMMENT '	0=NotPublished, 1=Published'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `google_drive_accounts`
--

CREATE TABLE `google_drive_accounts` (
  `id` int NOT NULL,
  `email` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `client_id` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `client_secret` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `refresh_token` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `access_token` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `expires_in` int NOT NULL,
  `status` int NOT NULL,
  `created_at` int NOT NULL,
  `updated_at` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `image_slider`
--

CREATE TABLE `image_slider` (
  `id` int NOT NULL,
  `title` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `banner` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `content_type` int NOT NULL COMMENT '0=Movie,1=WebSeries,2=WebView,3=External Browser',
  `content_id` int NOT NULL,
  `url` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `status` int NOT NULL COMMENT '	0=UnPublished, 1=Published'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_520_ci;

-- --------------------------------------------------------

--
-- Table structure for table `live_tv_channels`
--

CREATE TABLE `live_tv_channels` (
  `id` int NOT NULL,
  `name` text NOT NULL,
  `banner` text NOT NULL,
  `stream_type` text NOT NULL,
  `url` text NOT NULL,
  `content_type` int NOT NULL DEFAULT '3' COMMENT '	1=Movie, 2=WebSeries, 3=LiveTV',
  `type` int NOT NULL DEFAULT '0' COMMENT '	0=NotPremium, 1=Premium',
  `status` int NOT NULL DEFAULT '1' COMMENT '0=No, 1=Yes',
  `featured` int NOT NULL DEFAULT '0' COMMENT '0=No, 1=Yes',
  `user_agent` text NOT NULL,
  `referer` text NOT NULL,
  `cookie` text NOT NULL,
  `headers` longtext NOT NULL,
  `drm_uuid` text NOT NULL COMMENT 'WIDEVINE,PLAYREADY,CLEARKEY	',
  `drm_license_uri` text NOT NULL,
  `genres` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- --------------------------------------------------------

--
-- Table structure for table `live_tv_genres`
--

CREATE TABLE `live_tv_genres` (
  `id` int NOT NULL,
  `name` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `status` int NOT NULL COMMENT '0=NotPublished, 1=Published'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `mail_templates`
--

CREATE TABLE `mail_templates` (
  `id` int NOT NULL,
  `type` text NOT NULL,
  `data` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `mail_token_details`
--

CREATE TABLE `mail_token_details` (
  `id` int NOT NULL,
  `code` varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `token` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `mail` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `type` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `status` int NOT NULL DEFAULT '0' COMMENT '0=Not Used, 1=Used'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_520_ci;

-- --------------------------------------------------------

--
-- Table structure for table `movies`
--

CREATE TABLE `movies` (
  `id` int NOT NULL,
  `TMDB_ID` int NOT NULL,
  `name` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `description` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `genres` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `release_date` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `runtime` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `poster` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `banner` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `youtube_trailer` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `downloadable` int NOT NULL COMMENT '0=Not Downloadable, 1=Downloadable',
  `type` int NOT NULL COMMENT '0=NotPremium, 1=Premium',
  `status` int NOT NULL COMMENT '0=UnPublished, 1=Published',
  `content_type` int NOT NULL DEFAULT '1' COMMENT '1=Movie, 2=WebSeries'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_520_ci;

-- --------------------------------------------------------

--
-- Table structure for table `movie_download_links`
--

CREATE TABLE `movie_download_links` (
  `id` int NOT NULL,
  `name` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `size` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `quality` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `link_order` int NOT NULL,
  `movie_id` int NOT NULL,
  `url` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `type` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `download_type` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `status` int NOT NULL COMMENT '0=Not Released, 1=Released'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `movie_play_links`
--

CREATE TABLE `movie_play_links` (
  `id` int NOT NULL,
  `name` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `size` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `quality` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `link_order` int NOT NULL,
  `movie_id` int NOT NULL,
  `url` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `type` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `status` int NOT NULL COMMENT '0=Not Released, 1=Released',
  `skip_available` int NOT NULL DEFAULT '0' COMMENT '0=No, 1=Yes',
  `intro_start` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `intro_end` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `end_credits_marker` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `link_type` int NOT NULL COMMENT '0=NotPremium, 1=Premium',
  `drm_uuid` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL COMMENT 'WIDEVINE,PLAYREADY,CLEARKEY',
  `drm_license_uri` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_520_ci;

-- --------------------------------------------------------

--
-- Table structure for table `networks`
--

CREATE TABLE `networks` (
  `id` int NOT NULL,
  `name` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `logo` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `networks_order` int NOT NULL,
  `status` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `report`
--

CREATE TABLE `report` (
  `id` int NOT NULL,
  `user_id` int NOT NULL,
  `title` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `description` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `report_type` int NOT NULL COMMENT '0=Custom, 1=Movie, 2=Web Series, 3=Live TV',
  `status` int NOT NULL COMMENT '0=Pending, 1=Solved, 2=Canceled'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `request`
--

CREATE TABLE `request` (
  `id` int NOT NULL,
  `user_id` int NOT NULL,
  `title` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `description` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `type` int NOT NULL COMMENT '0=Custom, 1=Movie, 2=Web Series, 3=Live TV',
  `status` int NOT NULL COMMENT '0=Pending, 1=Accepted, 2=Rejected'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `search_list`
--

CREATE TABLE `search_list` (
  `id` int NOT NULL,
  `search_text` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `movies_found` int NOT NULL,
  `web_series_found` int NOT NULL,
  `timestamp` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `subscription`
--

CREATE TABLE `subscription` (
  `id` int NOT NULL,
  `name` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `time` int NOT NULL COMMENT 'Days',
  `amount` int NOT NULL,
  `currency` int NOT NULL COMMENT '0=INR,1=USD',
  `background` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `subscription_type` int NOT NULL DEFAULT '0' COMMENT '0=Default, 1=Remove Ads, 2=Play Premium, 3=Download Premium',
  `play_store_billing_product_id` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci,
  `status` int NOT NULL COMMENT '0=UnPublished, 1=Published'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_520_ci;

-- --------------------------------------------------------

--
-- Table structure for table `subscription_log`
--

CREATE TABLE `subscription_log` (
  `id` int NOT NULL,
  `name` text NOT NULL,
  `amount` int NOT NULL,
  `time` int NOT NULL,
  `subscription_start` date NOT NULL,
  `subscription_exp` date NOT NULL,
  `user_id` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- --------------------------------------------------------

--
-- Table structure for table `subtitles`
--

CREATE TABLE `subtitles` (
  `id` int NOT NULL,
  `content_id` int NOT NULL,
  `content_type` int NOT NULL COMMENT '1=Movie, 2=WebSeries',
  `language` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `subtitle_url` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `mime_type` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `status` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `upcoming_contents`
--

CREATE TABLE `upcoming_contents` (
  `id` int NOT NULL,
  `tmdb_id` int NOT NULL,
  `name` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `poster` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `trailer_url` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `release_date` timestamp NOT NULL,
  `type` int NOT NULL,
  `status` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `user_db`
--

CREATE TABLE `user_db` (
  `id` int NOT NULL,
  `name` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `email` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `password` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `role` int NOT NULL DEFAULT '0' COMMENT '0=User, 1=Admin, 2=SubAdmin, 3=Manager, 4=Editor',
  `active_subscription` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `subscription_type` int NOT NULL DEFAULT '0' COMMENT '0=Default, 1=Remove Ads, 2=Play Premium, 3=Download Premium',
  `time` int NOT NULL DEFAULT '0',
  `amount` int NOT NULL DEFAULT '0',
  `subscription_start` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `subscription_exp` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `device_id` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_520_ci;

-- --------------------------------------------------------

--
-- Table structure for table `view_log`
--

CREATE TABLE `view_log` (
  `id` int NOT NULL,
  `user_id` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `content_id` int NOT NULL,
  `content_type` int NOT NULL COMMENT '1=Movie, 2=WebSeries',
  `date` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `time` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `watch_log`
--

CREATE TABLE `watch_log` (
  `id` int NOT NULL,
  `user_id` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `content_id` int NOT NULL,
  `content_type` int NOT NULL COMMENT '1=Movie, 2=WebSeries'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `web_series`
--

CREATE TABLE `web_series` (
  `id` int NOT NULL,
  `TMDB_ID` int NOT NULL,
  `name` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `description` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `genres` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `release_date` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `poster` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `banner` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `youtube_trailer` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `downloadable` int NOT NULL,
  `type` int NOT NULL,
  `status` int NOT NULL,
  `content_type` int NOT NULL DEFAULT '2' COMMENT '1=Movie, 2=WebSeries'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_520_ci;

-- --------------------------------------------------------

--
-- Table structure for table `web_series_episoade`
--

CREATE TABLE `web_series_episoade` (
  `id` int NOT NULL,
  `Episoade_Name` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `episoade_image` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `episoade_description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `episoade_order` int NOT NULL,
  `season_id` int NOT NULL,
  `downloadable` int NOT NULL COMMENT '0=No, 1=Yes',
  `type` int NOT NULL COMMENT '0=NotPremium, 1=Premium',
  `status` int NOT NULL COMMENT '0=Not Released, 1=Released',
  `source` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `url` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `skip_available` int NOT NULL DEFAULT '0' COMMENT '0=No, 1=Yes',
  `intro_start` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `intro_end` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `end_credits_marker` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `drm_uuid` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL COMMENT 'WIDEVINE,PLAYREADY,CLEARKEY',
  `drm_license_uri` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_520_ci;

-- --------------------------------------------------------

--
-- Table structure for table `web_series_seasons`
--

CREATE TABLE `web_series_seasons` (
  `id` int NOT NULL,
  `Session_Name` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `season_order` int NOT NULL,
  `web_series_id` int NOT NULL,
  `status` int NOT NULL COMMENT '0=Not Released, 1=Released'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_520_ci;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `ci_sessions`
--
ALTER TABLE `ci_sessions`
  ADD PRIMARY KEY (`id`),
  ADD KEY `ci_sessions_timestamp` (`timestamp`);

--
-- Indexes for table `comments`
--
ALTER TABLE `comments`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `config`
--
ALTER TABLE `config`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `content_network_log`
--
ALTER TABLE `content_network_log`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `coupon`
--
ALTER TABLE `coupon`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `custom_payment_requests`
--
ALTER TABLE `custom_payment_requests`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `custom_payment_type`
--
ALTER TABLE `custom_payment_type`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `custom_tags`
--
ALTER TABLE `custom_tags`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `custom_tag_log`
--
ALTER TABLE `custom_tag_log`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `devices`
--
ALTER TABLE `devices`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `devices_log`
--
ALTER TABLE `devices_log`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `disposable_emails`
--
ALTER TABLE `disposable_emails`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `episode_download_links`
--
ALTER TABLE `episode_download_links`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `episode_play_links`
--
ALTER TABLE `episode_play_links`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `favourite`
--
ALTER TABLE `favourite`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `genres`
--
ALTER TABLE `genres`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `google_drive_accounts`
--
ALTER TABLE `google_drive_accounts`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `image_slider`
--
ALTER TABLE `image_slider`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `live_tv_channels`
--
ALTER TABLE `live_tv_channels`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `live_tv_genres`
--
ALTER TABLE `live_tv_genres`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `mail_templates`
--
ALTER TABLE `mail_templates`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `mail_token_details`
--
ALTER TABLE `mail_token_details`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `movies`
--
ALTER TABLE `movies`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `movie_download_links`
--
ALTER TABLE `movie_download_links`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `movie_play_links`
--
ALTER TABLE `movie_play_links`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `networks`
--
ALTER TABLE `networks`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `report`
--
ALTER TABLE `report`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `request`
--
ALTER TABLE `request`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `search_list`
--
ALTER TABLE `search_list`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `subscription`
--
ALTER TABLE `subscription`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `subscription_log`
--
ALTER TABLE `subscription_log`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `subtitles`
--
ALTER TABLE `subtitles`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `upcoming_contents`
--
ALTER TABLE `upcoming_contents`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `user_db`
--
ALTER TABLE `user_db`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `view_log`
--
ALTER TABLE `view_log`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `watch_log`
--
ALTER TABLE `watch_log`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `web_series`
--
ALTER TABLE `web_series`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `web_series_episoade`
--
ALTER TABLE `web_series_episoade`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `web_series_seasons`
--
ALTER TABLE `web_series_seasons`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `comments`
--
ALTER TABLE `comments`
  MODIFY `id` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `config`
--
ALTER TABLE `config`
  MODIFY `id` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `content_network_log`
--
ALTER TABLE `content_network_log`
  MODIFY `id` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `coupon`
--
ALTER TABLE `coupon`
  MODIFY `id` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `custom_payment_requests`
--
ALTER TABLE `custom_payment_requests`
  MODIFY `id` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `custom_payment_type`
--
ALTER TABLE `custom_payment_type`
  MODIFY `id` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `custom_tags`
--
ALTER TABLE `custom_tags`
  MODIFY `id` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `custom_tag_log`
--
ALTER TABLE `custom_tag_log`
  MODIFY `id` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `devices`
--
ALTER TABLE `devices`
  MODIFY `id` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `devices_log`
--
ALTER TABLE `devices_log`
  MODIFY `id` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `disposable_emails`
--
ALTER TABLE `disposable_emails`
  MODIFY `id` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `episode_download_links`
--
ALTER TABLE `episode_download_links`
  MODIFY `id` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `episode_play_links`
--
ALTER TABLE `episode_play_links`
  MODIFY `id` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `favourite`
--
ALTER TABLE `favourite`
  MODIFY `id` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `genres`
--
ALTER TABLE `genres`
  MODIFY `id` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `google_drive_accounts`
--
ALTER TABLE `google_drive_accounts`
  MODIFY `id` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `image_slider`
--
ALTER TABLE `image_slider`
  MODIFY `id` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `live_tv_channels`
--
ALTER TABLE `live_tv_channels`
  MODIFY `id` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `live_tv_genres`
--
ALTER TABLE `live_tv_genres`
  MODIFY `id` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `mail_templates`
--
ALTER TABLE `mail_templates`
  MODIFY `id` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `mail_token_details`
--
ALTER TABLE `mail_token_details`
  MODIFY `id` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `movies`
--
ALTER TABLE `movies`
  MODIFY `id` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `movie_download_links`
--
ALTER TABLE `movie_download_links`
  MODIFY `id` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `movie_play_links`
--
ALTER TABLE `movie_play_links`
  MODIFY `id` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `networks`
--
ALTER TABLE `networks`
  MODIFY `id` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `report`
--
ALTER TABLE `report`
  MODIFY `id` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `request`
--
ALTER TABLE `request`
  MODIFY `id` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `search_list`
--
ALTER TABLE `search_list`
  MODIFY `id` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `subscription`
--
ALTER TABLE `subscription`
  MODIFY `id` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `subscription_log`
--
ALTER TABLE `subscription_log`
  MODIFY `id` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `subtitles`
--
ALTER TABLE `subtitles`
  MODIFY `id` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `upcoming_contents`
--
ALTER TABLE `upcoming_contents`
  MODIFY `id` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `user_db`
--
ALTER TABLE `user_db`
  MODIFY `id` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `view_log`
--
ALTER TABLE `view_log`
  MODIFY `id` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `watch_log`
--
ALTER TABLE `watch_log`
  MODIFY `id` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `web_series`
--
ALTER TABLE `web_series`
  MODIFY `id` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `web_series_episoade`
--
ALTER TABLE `web_series_episoade`
  MODIFY `id` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `web_series_seasons`
--
ALTER TABLE `web_series_seasons`
  MODIFY `id` int NOT NULL AUTO_INCREMENT;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
