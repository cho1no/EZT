import { useState } from 'react';
import PropTypes from 'prop-types';
import { Carousel } from 'react-responsive-carousel';
import 'react-responsive-carousel/lib/styles/carousel.min.css';

import Card from '@mui/material/Card';
import Grid from '@mui/material/Grid';
import Typography from '@mui/material/Typography';

import { fDateTime } from 'src/utils/format-time';
import { fNumber } from 'src/utils/format-number';

import { boxStyle, styleColBox } from '../common-table/css';

export default function Request({ reqInfo }) {
  return (
    <Grid container spacing={2}>
      <Grid item md={8} sm={12}>
        <Card sx={styleColBox}>
          <ReqImg img={reqInfo.fileVO} />
        </Card>
      </Grid>
      <Grid item md={4} sm={12}>
        <Card sx={styleColBox}>
          <ReqShort info={reqInfo} />
        </Card>
      </Grid>
      <Grid item xs={12}>
        <Card sx={styleColBox}>
          <ReqLong info={reqInfo} />
        </Card>
      </Grid>
      <Grid item xs={12}>
        <Card sx={styleColBox}>
          <ReqAddr info={reqInfo} />
        </Card>
      </Grid>
    </Grid>
  );
}
Request.propTypes = {
  reqInfo: PropTypes.object,
};
function ReqShort({ info }) {
  return (
    <Grid container spacing={2}>
      <Grid item xs={12}>
        <Typography variant="subtitle1" p={1}>
          시공공간
        </Typography>
        <Typography style={boxStyle} variant="body1">
          {info.cttPlaceNm}
        </Typography>
        <Typography variant="subtitle1" p={1}>
          시공분야
        </Typography>
        <Typography style={boxStyle} variant="body1">
          {info.categoryCodeNm}
        </Typography>
        <Typography variant="subtitle1" p={1}>
          공간 상황
        </Typography>
        <Typography style={boxStyle} variant="body1">
          {info.cttPlaceSituationNm}
        </Typography>
        <Typography variant="subtitle1" p={1}>
          공간 면적
        </Typography>
        <Typography style={boxStyle} variant="body1">
          {info.cttPlaceArea} 평
        </Typography>
      </Grid>
    </Grid>
  );
}
ReqShort.propTypes = {
  info: PropTypes.object,
};
function ReqLong({ info }) {
  return (
    <Grid container spacing={2}>
      <Grid item xs={12}>
        <Typography variant="subtitle1" p={1}>
          의뢰 제목
        </Typography>
        <Typography style={boxStyle} variant="body1">
          {info.title}
        </Typography>
        <Grid container spacing={2}>
          <Grid item xs={6}>
            <Typography variant="subtitle1" p={1}>
              의뢰 희망 가격
            </Typography>
            <Typography style={boxStyle} variant="body1">
              {fNumber(info.hopeCttBudget)}원
            </Typography>
          </Grid>
          <Grid item xs={6}>
            <Typography variant="subtitle1" p={1}>
              의뢰 희망 일시
            </Typography>
            <Typography style={boxStyle} variant="body1">
              {fDateTime(info.hopeCttStartDt, 'yyyy/MM/dd')}&nbsp;~&nbsp;
              {fDateTime(info.hopeCttEndDt, 'yyyy/MM/dd')}
            </Typography>
          </Grid>
        </Grid>
        <Typography variant="subtitle1" p={1}>
          의뢰 내용
        </Typography>
        <Typography style={boxStyle} variant="body1">
          {info.content}
        </Typography>
        <Grid container spacing={2}>
          <Grid item xs={6}>
            <Typography variant="subtitle1" p={1}>
              작성일
            </Typography>
            <Typography style={boxStyle} variant="body1">
              {fDateTime(info.writeDt, 'yyyy/MM/dd')}
            </Typography>
          </Grid>
          <Grid item xs={6}>
            <Typography variant="subtitle1" p={1}>
              수정일
            </Typography>
            <Typography style={boxStyle} variant="body1">
              {fDateTime(info.updateDt, 'yyyy/MM/dd') || '수정 안함'}
            </Typography>
          </Grid>
        </Grid>
      </Grid>
    </Grid>
  );
}
ReqLong.propTypes = {
  info: PropTypes.object,
};
function ReqAddr({ info }) {
  return (
    <Grid container spacing={2}>
      <Grid item xs={12}>
        <Grid container spacing={2}>
          <Grid item xs={6}>
            <Typography variant="subtitle1" p={1}>
              의뢰자명
            </Typography>
            <Typography style={boxStyle} variant="body1">
              {info.usersName}
            </Typography>
          </Grid>
          <Grid item xs={6}>
            <Typography variant="subtitle1" p={1}>
              우편번호
            </Typography>
            <Typography style={boxStyle} variant="body1">
              {info.postcode}
            </Typography>
          </Grid>
        </Grid>
        <Typography variant="subtitle1" p={1}>
          주소
        </Typography>
        <Typography style={boxStyle} variant="body1">
          {info.address}
        </Typography>
        <Typography variant="subtitle1" p={1}>
          상세주소
        </Typography>
        <Typography style={boxStyle} variant="body1">
          {info.detailAddress}
        </Typography>
      </Grid>
    </Grid>
  );
}
ReqAddr.propTypes = {
  info: PropTypes.object,
};
function ReqImg({ img }) {
  const [currentIndex, setCurrentIndex] = useState();
  const renderSlides = img.map((image, idx) => (
    <div key={idx}>
      <img
        src={`/display?fileName=${image.savePath}/${image.saveName}_${image.originalFileName}.${image.ext}`}
        alt={image.originalFileName}
        style={{ borderRadius: '15px' }}
      />
    </div>
  ));
  function handleChange(index) {
    setCurrentIndex(index);
  }
  return (
    <Grid container spacing={2}>
      <Grid item xs={12}>
        <Carousel
          showArrows={false}
          showThumbs={false}
          dynamicHeight={false}
          selectedItem={img[currentIndex]}
          // eslint-disable-next-line react/jsx-no-bind
          onChange={handleChange}
          style={{ borderRadius: '25%' }}
        >
          {renderSlides}
        </Carousel>
      </Grid>
    </Grid>
  );
}
ReqImg.propTypes = {
  img: PropTypes.any,
};
