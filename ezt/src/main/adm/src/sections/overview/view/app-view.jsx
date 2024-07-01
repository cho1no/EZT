import axios from 'axios';
import { faker } from '@faker-js/faker';
import { useState, useEffect } from 'react';

import Container from '@mui/material/Container';
import Grid from '@mui/material/Unstable_Grid2';
import Typography from '@mui/material/Typography';

import Iconify from 'src/components/iconify';

import AppTasks from '../app-tasks';
import AppNewsUpdate from '../app-news-update';
import AppOrderTimeline from '../app-order-timeline';
import AppCurrentVisits from '../app-current-visits';
import AppWebsiteVisits from '../app-website-visits';
import AppWidgetSummary from '../app-widget-summary';
import AppTrafficBySite from '../app-traffic-by-site';
// import AppCurrentSubject from '../app-current-subject';
import AppConversionRates from '../app-conversion-rates';

// ----------------------------------------------------------------------
export default function AppView() {
  const [statistic, setStatistic] = useState([]);
  const [joinData, setJoinData] = useState({ dates: [], group: { user: [], worker: [] } });
  const [reqCategoryData, setReqCategoryData] = useState([{ label: [], value: [] }]);
  const [reqRegionData, setReqRegionData] = useState([{ label: [], value: [] }]);
  const getStatistic = async () => setStatistic((await axios.get('/adm/getStatistic')).data);
  const formattingJoinData = (data) => {
    const temp = {};
    data.forEach((e, i) => {
      if (!temp[e.DT]) {
        temp[e.DT] = { user: 0, worker: 0 };
      }
      if (e.ROLE === 'ROLE_USER') {
        temp[e.DT].user += e.NUM;
      } else if (e.ROLE === 'ROLE_WORKER') {
        temp[e.DT].worker += e.NUM;
      }
    });
    // 새로운 배열 생성
    const newDates = [];
    const newUserGroup = [];
    const newWorkerGroup = [];

    Object.keys(temp).forEach((date) => {
      if (!newDates.includes(date)) {
        newDates.push(date);
      }
      newUserGroup.push(temp[date].user);
      newWorkerGroup.push(temp[date].worker);
    });
    return {
      dates: newDates,
      group: {
        user: newUserGroup,
        worker: newWorkerGroup,
      },
    };
  };
  const formattingReqData = (data) =>
    data.map((e) => ({
      label: e.CATEGORY ? e.CATEGORY : e.REGION,
      value: e.NUM,
    }));
  useEffect(() => {
    getStatistic();
  }, []);
  useEffect(() => {
    const { newJoin = [], reqCategory = [], reqRegion = [] } = statistic;
    setJoinData(formattingJoinData(newJoin));
    setReqCategoryData(formattingReqData(reqCategory));
    setReqRegionData(formattingReqData(reqRegion));
  }, [statistic]);
  return (
    <Container maxWidth="xl">
      <Typography variant="h4" sx={{ mb: 5 }}>
        관리자님 환영합니다 👋
      </Typography>

      <Grid container spacing={3}>
        {/* <Grid xs={12} sm={6} md={3}>
          <AppWidgetSummary
            title="Weekly Sales"
            total={714000}
            color="success"
            icon={<img alt="icon" src="/assets/icons/glass/ic_glass_bag.png" />}
          />
          <AppWidgetSummary
            title="New Users"
            total={1352831}
            color="info"
            sx={{ mt: 3 }}
            icon={<img alt="icon" src="/assets/icons/glass/ic_glass_users.png" />}
          />
        </Grid>

        <Grid xs={12} sm={6} md={3}>
          <AppWidgetSummary
            title="New Users"
            total={1352831}
            color="info"
            icon={<img alt="icon" src="/assets/icons/glass/ic_glass_users.png" />}
          />
        </Grid>

        <Grid xs={12} sm={6} md={3}>
          <AppWidgetSummary
            title="Item Orders"
            total={1723315}
            color="warning"
            icon={<img alt="icon" src="/assets/icons/glass/ic_glass_buy.png" />}
          />
        </Grid>

        <Grid xs={12} sm={6} md={3}>
          <AppWidgetSummary
            title="Bug Reports"
            total={234}
            color="error"
            sx={{ mb: 2 }}
            icon={<img alt="icon" src="/assets/icons/glass/ic_glass_message.png" />}
          />
          <AppWidgetSummary
            title="New Users"
            total={1352831}
            color="info"
            icon={<img alt="icon" src="/assets/icons/glass/ic_glass_users.png" />}
          />
        </Grid> */}

        <Grid xs={12} md={8} lg={8}>
          <AppWebsiteVisits
            title="일별 신규 가입자"
            // subheader="(+43%) than last year"
            chart={{
              labels: joinData.dates,
              series: [
                {
                  name: '작업자',
                  type: 'area',
                  fill: 'gradient',
                  data: joinData.group.worker,
                },
                {
                  name: '일반 유저',
                  type: 'area',
                  fill: 'gradient',
                  data: joinData.group.user,
                },
              ],
            }}
          />
        </Grid>

        <Grid container xs={12} sm={6} md={4} spacing={2}>
          <Grid xs={12}>
            <AppWidgetSummary
              title="전체 유저"
              total={1352831}
              color="info"
              icon={<img alt="icon" src="/assets/icons/glass/ic_glass_users.png" />}
            />
          </Grid>
          <Grid xs={12}>
            <AppWidgetSummary
              title="Bug Reports"
              total={234}
              color="error"
              icon={<img alt="icon" src="/assets/icons/glass/ic_glass_message.png" />}
            />
          </Grid>
          <Grid xs={12}>
            <AppWidgetSummary
              title="Bug Reports"
              total={234}
              color="error"
              icon={<img alt="icon" src="/assets/icons/glass/ic_glass_message.png" />}
            />
          </Grid>
        </Grid>
        {/* <Grid xs={12} md={6} lg={4}>
          <AppCurrentSubject
            title="Current Subject"
            chart={{
              categories: ['English', 'History', 'Physics', 'Geography', 'Chinese', 'Math'],
              series: [
                { name: 'Series 1', data: [80, 50, 30, 40, 100, 20] },
                { name: 'Series 2', data: [20, 30, 40, 80, 20, 80] },
                { name: 'Series 3', data: [44, 76, 78, 13, 43, 10] },
              ],
            }}
          />
        </Grid> */}
        <Grid xs={12} md={6} lg={4}>
          <AppCurrentVisits
            title="지역별 의뢰 현황"
            chart={{
              series: reqRegionData,
            }}
          />
        </Grid>

        <Grid xs={12} md={6} lg={8}>
          <AppConversionRates
            title="분야별 의뢰 현황"
            // subheader="(+43%) than last year"
            chart={{
              series: reqCategoryData,
            }}
          />
        </Grid>

        <Grid xs={12} md={6} lg={8}>
          <AppNewsUpdate
            title="News Update"
            list={[...Array(5)].map((_, index) => ({
              id: faker.string.uuid(),
              title: faker.person.jobTitle(),
              description: faker.commerce.productDescription(),
              image: `/assets/images/covers/cover_${index + 1}.jpg`,
              postedAt: faker.date.recent(),
            }))}
          />
        </Grid>

        <Grid xs={12} md={6} lg={4}>
          <AppOrderTimeline
            title="Order Timeline"
            list={[...Array(5)].map((_, index) => ({
              id: faker.string.uuid(),
              title: [
                '1983, orders, $4220',
                '12 Invoices have been paid',
                'Order #37745 from September',
                'New order placed #XF-2356',
                'New order placed #XF-2346',
              ][index],
              type: `order${index + 1}`,
              time: faker.date.past(),
            }))}
          />
        </Grid>

        <Grid xs={12} md={6} lg={4}>
          <AppTrafficBySite
            title="Traffic by Site"
            list={[
              {
                name: 'FaceBook',
                value: 323234,
                icon: <Iconify icon="eva:facebook-fill" color="#1877F2" width={32} />,
              },
              {
                name: 'Google',
                value: 341212,
                icon: <Iconify icon="eva:google-fill" color="#DF3E30" width={32} />,
              },
              {
                name: 'Linkedin',
                value: 411213,
                icon: <Iconify icon="eva:linkedin-fill" color="#006097" width={32} />,
              },
              {
                name: 'Twitter',
                value: 443232,
                icon: <Iconify icon="eva:twitter-fill" color="#1C9CEA" width={32} />,
              },
            ]}
          />
        </Grid>

        <Grid xs={12} md={6} lg={8}>
          <AppTasks
            title="Tasks"
            list={[
              { id: '1', name: 'Create FireStone Logo' },
              { id: '2', name: 'Add SCSS and JS files if required' },
              { id: '3', name: 'Stakeholder Meeting' },
              { id: '4', name: 'Scoping & Estimations' },
              { id: '5', name: 'Sprint Showcase' },
            ]}
          />
        </Grid>
      </Grid>
    </Container>
  );
}
