<template>
  <v-main>
    <v-container class="fill-height" fluid>
      <v-row align="center" justify="center">
        <v-col cols="12" sm="2">
          <v-select v-model="selectItem" :items="items" @change="changeRadius" label="Outlined style" outlined></v-select>
        </v-col>
        <v-col cols="12" sm="5">
          <v-card class="elevation-12">
            <v-text-field label="주소 입력" v-model="searchText" prepend-icon="mdi-account" type="text" :dense="true"></v-text-field>
          </V-card>
        </v-col>
        <v-col cols="12" sm="1">
          <v-btn text color="black" @click="searchPlace()">검색</v-btn>
        </v-col>
      </v-row>
      <v-row align="center" justify="center">
        <div id="map" style="width: 1200px; height: 400px"></div>
      </v-row>
    </v-container>
  </v-main>
</template>

<script>

import postscribe from 'postscribe'
import { mapGetters } from 'vuex'
import axios from 'axios'
import {
  SET_LOGIN_LOACTION_XY
} from '@/store/mutation-types.js'

export default {
  name: 'Index',
  data: function () {
    return {
      selectItem: 100,
      items: [
        { text: '100m', value: 100 },
        { text: '200m', value: 200 },
        { text: '300m', value: 300 }
      ],
      map: null,
      markers: [],
      centerMarker: null,
      circle: null,
      searchText: ''
    }
  },
  computed: {
    ...mapGetters([
      'getLoginLocationX',
      'getLoginLocationY',
      'getLoginId'
    ])
  },
  methods: {
    initScript: function () {
      // console.log('initScript')
      var src = '<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=27182d803bc31145e52687dda5546c30&libraries=services"></scrip'
      src = src + 't>'

      var initfunction = this.initMap

      postscribe(document.head, src, {
        done: initfunction
      })
    },
    initMap: function () {
      // console.log('initMap')
      this.getMap(this.getNewDefaultPosition())
      this.getCenterMarker()
      this.getCircle()
      this.getMarkersFromAddress()
    },
    getMap: function (position) {
      var map = null

      if (this.map === null) {
        var container = document.getElementById('map')
        var options = {
          center: position,
          level: 3
        }

        map = new window.kakao.maps.Map(container, options)
        this.map = map
      } else {
        map = this.map
      }

      return map
    },
    getNewDefaultPosition: function () {
      return new window.kakao.maps.LatLng(this.getLoginLocationX, this.getLoginLocationY)
    },
    getCenterMarker: function () {
      var center = null

      if (this.centerMarker === null) {
        center = new window.kakao.maps.Marker({
          map: this.map,
          position: this.getNewDefaultPosition()
        })

        this.centerMarker = center
      } else {
        center = this.this.centerMarker
      }

      return center
    },
    getNewMarker: function (position) {
      var marker = new window.kakao.maps.Marker({
        map: this.map,
        position: position
      })

      return marker
    },
    getCircle: function () {
      var circle = null

      if (this.circle === null) {
        circle = new window.kakao.maps.Circle({
          center: this.getNewDefaultPosition(),
          radius: this.selectItem,
          strokeWeight: 2,
          strokeColor: '#FF0000',
          strokeOpacity: 0.8,
          strokeStyle: 'dashed',
          fillColor: '#00ECF4',
          fillOpacity: 0.1
        })

        circle.setMap(this.map)
        this.circle = circle
      } else {
        circle = this.circle
      }

      return circle
    },
    getMarkersFromAddress: function () {
      var geocoder = new window.kakao.maps.services.Geocoder()
      var position = this.centerMarker.getPosition()
      var setMarker = this.setMarker
      geocoder.coord2Address(position.getLng(), position.getLat(), function (result, status) {
        if (status === window.kakao.maps.services.Status.OK) {
          var address = result[0].address
          if (address.region_1depth_name === '서울') {
            var type = address.region_2depth_name
            setMarker(type)
          }
        }
      })
    },
    removeMarkers: function () {
      var len = this.markers.length
      if (len > 0) {
        for (var i = 0; i < len; i++) {
          var marker = this.markers[i]
          marker.setMap(null)
        }

        this.markers = []
      }
    },
    setMarker: function (type) {
      axios.get(`http://localhost:7777/craw/${type}`).then(res => {
        var len = res.data.length
        var geocoder = new window.kakao.maps.services.Geocoder()
        var markers = this.markers
        var setViewMarker = this.setViewMarker
        var getMarker = this.getNewMarker
        this.removeMarkers()

        for (var i = 0; i < len; i++) {
          var data = res.data[i]
          // console.log('name : ' + data.locationName)
          geocoder.addressSearch(data.address, function (result, status) {
            if (status === window.kakao.maps.services.Status.OK) {
              var position = new window.kakao.maps.LatLng(result[0].y, result[0].x)
              var marker = getMarker(position)

              marker.setVisible(false)
              setViewMarker(marker)
              markers.push(marker)
            }
          })
        }
      }).catch(err => {
        console.log(err)
      })
    },
    setViewMarker: function (marker) {
      var center = this.circle.getPosition()
      var radius = this.circle.getRadius()
      var line = new window.kakao.maps.Polyline()
      var path = [marker.getPosition(), center]
      line.setPath(path)

      var dist = line.getLength()
      // console.log('radius : ' + radius)
      // console.log('dist : ' + dist)

      if (dist <= radius) {
        marker.setVisible(true)
      } else {
        marker.setVisible(false)
      }
    },
    changeRadius: function () {
      var len = this.markers.length
      this.circle.setRadius(this.selectItem)

      for (var i = 0; i < len; i++) {
        var marker = this.markers[i]
        this.setViewMarker(marker)
      }
    },
    setCenterMarkerPosition: function (position) {
      this.centerMarker.setPosition(position)
    },
    setCirclePosition: function (position) {
      this.circle.setPosition(position)
    },
    searchPlace: function () {
      var places = new window.kakao.maps.services.Places()
      var map = this.map

      var setMarker = this.setMarker
      var setCenterMarkerPosition = this.setCenterMarkerPosition
      var setCirclePosition = this.setCirclePosition
      var getLoginId = this.getLoginId

      var store = this.$store

      places.keywordSearch(this.searchText, function (result, status) {
        if (status === window.kakao.maps.services.Status.OK) {
          console.log(result)

          var len = result.length
          var type = null
          var x = -1
          var y = -1

          for (var i = 0; i < len; i++) {
            var address = result[i].road_address_name

            if (address.split(' ')[0] === ('서울')) {
              type = address.split(' ')[0]
              x = result[i].y
              y = result[i].x
              break
            }
          }

          if (type === null) {
            alert('검색범위는 서울내로 한정합니다')
          } else {
            var position = new window.kakao.maps.LatLng(x, y)
            map.panTo(position)

            setCenterMarkerPosition(position)
            setCirclePosition(position)
            setMarker(type)

            var id = getLoginId
            if (id != null) {
              store.commit(SET_LOGIN_LOACTION_XY, { x: x, y: y })
              axios.put(`http://localhost:7777/member/coordinate/${id}`, { x: x, y: y }).then(res => {
                if (res.status === 200) {
                  console.log(res)
                }
              }).catch(err => {
                console.log(err)
              })
            }
          }
        }
      })
    }
  },
  mounted: function () {
    if (!window.kakao || !window.kakao.maps) {
      this.initScript()
    } else {
      this.initMap()
    }
  }
}
</script>
