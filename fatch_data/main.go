package main

import (
	"fmt"

	resty "gopkg.in/resty.v1"
)

func main() {

	address := []string{
		"สนามบินสุวรรณภูมิ",
		"มหาวิทยาลัยราชภัฏกาญจนบุรี",
		"มหาวิทยาลัยราชภัฏกำแพงเพชร",
		"มหาวิทยาลัยราชภัฏจันทรเกษม",
		"มหาวิทยาลัยราชภัฏชัยภูมิ",
		"มหาวิทยาลัยราชภัฏเชียงราย",
		"มหาวิทยาลัยราชภัฏเชียงใหม่",
		"มหาวิทยาลัยราชภัฏเทพสตรี",
		"มหาวิทยาลัยราชภัฏธนบุรี",
		"มหาวิทยาลัยราชภัฏนครปฐม",
		"มหาวิทยาลัยราชภัฏนครราชสีมา",
		"มหาวิทยาลัยราชภัฏนครศรีธรรมราช",
		"มหาวิทยาลัยราชภัฏนครสวรรค์",
		"มหาวิทยาลัยราชภัฏบ้านสมเด็จเจ้าพระยา",
		"มหาวิทยาลัยราชภัฏบุรีรัมย์",
		"มหาวิทยาลัยราชภัฏพระนคร",
		"มหาวิทยาลัยราชภัฏพระนครศรีอยุธยา",
		"มหาวิทยาลัยราชภัฏพิบูลสงคราม",
		"มหาวิทยาลัยราชภัฏเพชรบุรี",
		"มหาวิทยาลัยราชภัฏเพชรบูรณ์",
		"มหาวิทยาลัยราชภัฏภูเก็ต",
		"มหาวิทยาลัยราชภัฏมหาสารคาม",
		"มหาวิทยาลัยราชภัฏยะลา",
		"มหาวิทยาลัยราชภัฏร้อยเอ็ด",
		"มหาวิทยาลัยราชภัฏราชนครินทร์",
		"มหาวิทยาลัยราชภัฏรำไพพรรณี",
		"มหาวิทยาลัยราชภัฏลำปาง",
		"มหาวิทยาลัยราชภัฏเลย",
		"มหาวิทยาลัยราชภัฏวไลยอลงกรณ์ ในพระบรมราชูปถัมภ์",
		"มหาวิทยาลัยราชภัฏศรีสะเกษ",
		"มหาวิทยาลัยราชภัฏสกลนคร",
		"มหาวิทยาลัยราชภัฏสงขลา",
		"มหาวิทยาลัยราชภัฏสวนสุนันทา",
		"มหาวิทยาลัยราชภัฏสุราษฎร์ธานี",
		"มหาวิทยาลัยราชภัฏสุรินทร์",
		"มหาวิทยาลัยราชภัฏหมู่บ้านจอมบึง",
		"มหาวิทยาลัยราชภัฏอุดรธานี",
		"มหาวิทยาลัยราชภัฏอุตรดิตถ์",
		"มหาวิทยาลัยราชภัฏอุบลราชธานี",
		"มหาวิทยาลัยเทคโนโลยีราชมงคลกรุงเทพ",
		"มหาวิทยาลัยเทคโนโลยีราชมงคลตะวันออก",
		"มหาวิทยาลัยเทคโนโลยีราชมงคลธัญบุรี",
		"มหาวิทยาลัยเทคโนโลยีราชมงคลพระนคร",
		"มหาวิทยาลัยเทคโนโลยีราชมงคลรัตนโกสินทร์",
		"มหาวิทยาลัยเทคโนโลยีราชมงคลล้านนา",
		"มหาวิทยาลัยเทคโนโลยีราชมงคลศรีวิชัย",
		"มหาวิทยาลัยเทคโนโลยีราชมงคลสุวรรณภูมิ",
		"มหาวิทยาลัยเทคโนโลยีราชมงคลอีสาน",
		"จุฬาลงกรณ์มหาวิทยาลัย",
		"มหาวิทยาลัยเกษตรศาสตร์",
		"มหาวิทยาลัยขอนแก่น",
		"มหาวิทยาลัยเชียงใหม่",
		"มหาวิทยาลัยทักษิณ",
		"มหาวิทยาลัยเทคโนโลยีพระจอมเกล้าธนบุรี",
		"มหาวิทยาลัยเทคโนโลยีพระจอมเกล้าพระนครเหนือ",
		"มหาวิทยาลัยเทคโนโลยีสุรนารี",
		"มหาวิทยาลัยธรรมศาสตร์",
		"มหาวิทยาลัยนวมินทราธิราช",
		"มหาวิทยาลัยบูรพา",
		"มหาวิทยาลัยพะเยา",
		"มหาวิทยาลัยมหาจุฬาลงกรณราชวิทยาลัย",
		"มหาวิทยาลัยมหามกุฏราชวิทยาลัย",
		"มหาวิทยาลัยมหิดล",
		"มหาวิทยาลัยแม่โจ้",
		"มหาวิทยาลัยแม่ฟ้าหลวง",
		"มหาวิทยาลัยวลัยลักษณ์",
		"มหาวิทยาลัยศรีนครินทรวิโรฒ",
		"มหาวิทยาลัยศิลปากร",
		"มหาวิทยาลัยสงขลานครินทร์",
		"มหาวิทยาลัยสวนดุสิต",
		"มหาวิทยาลัยกรุงเทพ",
		"มหาวิทยาลัยกรุงเทพธนบุรี",
		"มหาวิทยาลัยกรุงเทพสุวรรณภูมิ",
		"มหาวิทยาลัยการจัดการและเทคโนโลยีอีสเทิร์น",
		"มหาวิทยาลัยเกริก",
		"มหาวิทยาลัยเกษมบัณฑิต",
		"มหาวิทยาลัยคริสเตียน",
		"มหาวิทยาลัยเฉลิมกาญจนา",
		"มหาวิทยาลัยตาปี",
		"มหาวิทยาลัยเจ้าพระยา",
		"มหาวิทยาลัยชินวัตร",
		"มหาวิทยาลัยเซนต์จอห์น",
		"มหาวิทยาลัยเทคโนโลยีมหานคร",
		"มหาวิทยาลัยธนบุรี",
		"มหาวิทยาลัยธุรกิจบัณฑิตย์",
		"มหาวิทยาลัยนอร์ทกรุงเทพ",
		"มหาวิทยาลัยนอร์ท-เชียงใหม่",
		"มหาวิทยาลัยนานาชาติแสตมฟอร์ด",
		"มหาวิทยาลัยนานาชาติเอเชีย-แปซิฟิก",
		"มหาวิทยาลัยเนชั่น",
		"มหาวิทยาลัยปทุมธานี",
		"มหาวิทยาลัยพายัพ",
		"มหาวิทยาลัยพิษณุโลก",
		"มหาวิทยาลัยฟาฏอนี",
		"มหาวิทยาลัยฟาร์อีสเทอร์น",
		"มหาวิทยาลัยภาคกลาง",
		"มหาวิทยาลัยภาคตะวันออกเฉียงเหนือ",
		"มหาวิทยาลัยรังสิต",
		"มหาวิทยาลัยรัตนบัณฑิต",
		"มหาวิทยาลัยราชธานี",
		"มหาวิทยาลัยราชพฤกษ์",
		"มหาวิทยาลัยวงษ์ชวลิตกุล",
		"มหาวิทยาลัยเว็บสเตอร์ (ประเทศไทย)",
		"มหาวิทยาลัยเวสเทิร์น",
		"มหาวิทยาลัยศรีปทุม",
		"มหาวิทยาลัยสยาม",
		"มหาวิทยาลัยหอการค้าไทย",
		"มหาวิทยาลัยหัวเฉียวเฉลิมพระเกียรติ",
		"มหาวิทยาลัยหาดใหญ่",
		"มหาวิทยาลัยอัสสัมชัญ",
		"มหาวิทยาลัยอีสเทิร์นเอเชีย",
		"มหาวิทยาลัยเอเชียอาคเนย์",
	}

	arr1 := filterAddress(address, 0, 8)
	destinations1 := getDestinations(arr1)
	arr2 := filterAddress(address, 8, 8)
	destinations2 := getDestinations(arr2)
	arr3 := filterAddress(address, 16, 8)
	destinations3 := getDestinations(arr3)
	arr4 := filterAddress(address, 24, 8)
	destinations4 := getDestinations(arr4)
	arr5 := filterAddress(address, 32, 9)
	destinations5 := getDestinations(arr5)

	// data := make([]map[string]interface{}, 0)
	// temp1 := map[string]interface{}{}
	// temp2 := map[string]interface{}{}
	// temp3 := map[string]interface{}{}
	// temp4 := map[string]interface{}{}
	// temp5 := map[string]interface{}{}
	// temp6 := map[string]interface{}{}
	// temp7 := map[string]interface{}{}
	// temp8 := map[string]interface{}{}
	// temp9 := map[string]interface{}{}
	// temp10 := map[string]interface{}{}
	// temp11 := map[string]interface{}{}
	// temp12 := map[string]interface{}{}
	// temp13 := map[string]interface{}{}
	// temp14 := map[string]interface{}{}
	// temp15 := map[string]interface{}{}

	fmt.Println(destinations1)
	fmt.Println()
	fmt.Println()
	fmt.Println(destinations2)
	fmt.Println()
	fmt.Println()
	fmt.Println(destinations3)
	fmt.Println()
	fmt.Println()
	fmt.Println(destinations4)
	fmt.Println()
	fmt.Println()
	fmt.Println(destinations5)
	fmt.Println()
	fmt.Println()

	type Data struct {
		Results []struct {
			AddressComponents []struct {
				LongName  string   `json:"long_name"`
				ShortName string   `json:"short_name"`
				Types     []string `json:"types"`
			} `json:"address_components"`
			FormattedAddress string `json:"formatted_address"`
			Geometry         struct {
				Bounds struct {
					Northeast struct {
						Lat float64 `json:"lat"`
						Lng float64 `json:"lng"`
					} `json:"northeast"`
					Southwest struct {
						Lat float64 `json:"lat"`
						Lng float64 `json:"lng"`
					} `json:"southwest"`
				} `json:"bounds"`
				Location struct {
					Lat float64 `json:"lat"`
					Lng float64 `json:"lng"`
				} `json:"location"`
				LocationType string `json:"location_type"`
				Viewport     struct {
					Northeast struct {
						Lat float64 `json:"lat"`
						Lng float64 `json:"lng"`
					} `json:"northeast"`
					Southwest struct {
						Lat float64 `json:"lat"`
						Lng float64 `json:"lng"`
					} `json:"southwest"`
				} `json:"viewport"`
			} `json:"geometry"`
			PlaceID string   `json:"place_id"`
			Types   []string `json:"types"`
		} `json:"results"`
		Status string `json:"status"`
	}

	for index, adr := range address {
		data := Data{}

		_, _ = resty.R().
			SetQueryParams(map[string]string{
				"address": adr,
				"key":     "AIzaSyAli5QrCpRYAo64purb3ufAKICZyXWcjj8",
			}).
			SetHeader("Content-Type", "application/json").
			SetResult(&data).
			Get("https://maps.googleapis.com/maps/api/geocode/json")
		fmt.Println(data.Results[0].Geometry.Location.Lat, data.Results[0].Geometry.Location.Lng)

		if index == 40 {
			break
		}
	}

	// _, _ = resty.R().
	// 	SetQueryParams(map[string]string{
	// 		"units":        "matricts",
	// 		"origins":      destinations1,
	// 		"destinations": destinations2,
	// 		"key":          "AIzaSyAli5QrCpRYAo64purb3ufAKICZyXWcjj8",
	// 	}).
	// 	SetHeader("Content-Type", "application/json").
	// 	SetResult(&temp1).
	// 	Get("https://maps.googleapis.com/maps/api/distancematrix/json")

	// data = append(data, temp1)

	// _, _ = resty.R().
	// 	SetQueryParams(map[string]string{
	// 		"units":        "matricts",
	// 		"origins":      destinations1,
	// 		"destinations": destinations3,
	// 		"key":          "AIzaSyAli5QrCpRYAo64purb3ufAKICZyXWcjj8",
	// 	}).
	// 	SetHeader("Content-Type", "application/json").
	// 	SetResult(&temp2).
	// 	Get("https://maps.googleapis.com/maps/api/distancematrix/json")

	// data = append(data, temp2)

	// _, _ = resty.R().
	// 	SetQueryParams(map[string]string{
	// 		"units":        "matricts",
	// 		"origins":      destinations1,
	// 		"destinations": destinations4,
	// 		"key":          "AIzaSyAli5QrCpRYAo64purb3ufAKICZyXWcjj8",
	// 	}).
	// 	SetHeader("Content-Type", "application/json").
	// 	SetResult(&temp3).
	// 	Get("https://maps.googleapis.com/maps/api/distancematrix/json")

	// data = append(data, temp3)

	// _, _ = resty.R().
	// 	SetQueryParams(map[string]string{
	// 		"units":        "matricts",
	// 		"origins":      destinations1,
	// 		"destinations": destinations5,
	// 		"key":          "AIzaSyAli5QrCpRYAo64purb3ufAKICZyXWcjj8",
	// 	}).
	// 	SetHeader("Content-Type", "application/json").
	// 	SetResult(&temp4).
	// 	Get("https://maps.googleapis.com/maps/api/distancematrix/json")

	// data = append(data, temp4)

	// _, _ = resty.R().
	// 	SetQueryParams(map[string]string{
	// 		"units":        "matricts",
	// 		"origins":      destinations2,
	// 		"destinations": destinations3,
	// 		"key":          "AIzaSyAli5QrCpRYAo64purb3ufAKICZyXWcjj8",
	// 	}).
	// 	SetHeader("Content-Type", "application/json").
	// 	SetResult(&temp5).
	// 	Get("https://maps.googleapis.com/maps/api/distancematrix/json")

	// data = append(data, temp5)

	// _, _ = resty.R().
	// 	SetQueryParams(map[string]string{
	// 		"units":        "matricts",
	// 		"origins":      destinations2,
	// 		"destinations": destinations4,
	// 		"key":          "AIzaSyAli5QrCpRYAo64purb3ufAKICZyXWcjj8",
	// 	}).
	// 	SetHeader("Content-Type", "application/json").
	// 	SetResult(&temp6).
	// 	Get("https://maps.googleapis.com/maps/api/distancematrix/json")

	// data = append(data, temp6)

	// _, _ = resty.R().
	// 	SetQueryParams(map[string]string{
	// 		"units":        "matricts",
	// 		"origins":      destinations2,
	// 		"destinations": destinations5,
	// 		"key":          "AIzaSyAli5QrCpRYAo64purb3ufAKICZyXWcjj8",
	// 	}).
	// 	SetHeader("Content-Type", "application/json").
	// 	SetResult(&temp7).
	// 	Get("https://maps.googleapis.com/maps/api/distancematrix/json")

	// data = append(data, temp7)

	// _, _ = resty.R().
	// 	SetQueryParams(map[string]string{
	// 		"units":        "matricts",
	// 		"origins":      destinations3,
	// 		"destinations": destinations4,
	// 		"key":          "AIzaSyAli5QrCpRYAo64purb3ufAKICZyXWcjj8",
	// 	}).
	// 	SetHeader("Content-Type", "application/json").
	// 	SetResult(&temp8).
	// 	Get("https://maps.googleapis.com/maps/api/distancematrix/json")

	// data = append(data, temp8)

	// _, _ = resty.R().
	// 	SetQueryParams(map[string]string{
	// 		"units":        "matricts",
	// 		"origins":      destinations3,
	// 		"destinations": destinations5,
	// 		"key":          "AIzaSyAli5QrCpRYAo64purb3ufAKICZyXWcjj8",
	// 	}).
	// 	SetHeader("Content-Type", "application/json").
	// 	SetResult(&temp9).
	// 	Get("https://maps.googleapis.com/maps/api/distancematrix/json")

	// data = append(data, temp9)

	// _, _ = resty.R().
	// 	SetQueryParams(map[string]string{
	// 		"units":        "matricts",
	// 		"origins":      destinations4,
	// 		"destinations": destinations5,
	// 		"key":          "AIzaSyAli5QrCpRYAo64purb3ufAKICZyXWcjj8",
	// 	}).
	// 	SetHeader("Content-Type", "application/json").
	// 	SetResult(&temp10).
	// 	Get("https://maps.googleapis.com/maps/api/distancematrix/json")

	// data = append(data, temp10)

	// _, _ = resty.R().
	// 	SetQueryParams(map[string]string{
	// 		"units":        "matricts",
	// 		"origins":      destinations1,
	// 		"destinations": destinations1,
	// 		"key":          "AIzaSyAli5QrCpRYAo64purb3ufAKICZyXWcjj8",
	// 	}).
	// 	SetHeader("Content-Type", "application/json").
	// 	SetResult(&temp11).
	// 	Get("https://maps.googleapis.com/maps/api/distancematrix/json")

	// data = append(data, temp11)

	// _, _ = resty.R().
	// 	SetQueryParams(map[string]string{
	// 		"units":        "matricts",
	// 		"origins":      destinations2,
	// 		"destinations": destinations2,
	// 		"key":          "AIzaSyAli5QrCpRYAo64purb3ufAKICZyXWcjj8",
	// 	}).
	// 	SetHeader("Content-Type", "application/json").
	// 	SetResult(&temp12).
	// 	Get("https://maps.googleapis.com/maps/api/distancematrix/json")

	// data = append(data, temp12)

	// _, _ = resty.R().
	// 	SetQueryParams(map[string]string{
	// 		"units":        "matricts",
	// 		"origins":      destinations3,
	// 		"destinations": destinations3,
	// 		"key":          "AIzaSyAli5QrCpRYAo64purb3ufAKICZyXWcjj8",
	// 	}).
	// 	SetHeader("Content-Type", "application/json").
	// 	SetResult(&temp13).
	// 	Get("https://maps.googleapis.com/maps/api/distancematrix/json")

	// data = append(data, temp13)

	// _, _ = resty.R().
	// 	SetQueryParams(map[string]string{
	// 		"units":        "matricts",
	// 		"origins":      destinations4,
	// 		"destinations": destinations4,
	// 		"key":          "AIzaSyAli5QrCpRYAo64purb3ufAKICZyXWcjj8",
	// 	}).
	// 	SetHeader("Content-Type", "application/json").
	// 	SetResult(&temp14).
	// 	Get("https://maps.googleapis.com/maps/api/distancematrix/json")

	// data = append(data, temp14)

	// _, _ = resty.R().
	// 	SetQueryParams(map[string]string{
	// 		"units":        "matricts",
	// 		"origins":      destinations5,
	// 		"destinations": destinations5,
	// 		"key":          "AIzaSyAli5QrCpRYAo64purb3ufAKICZyXWcjj8",
	// 	}).
	// 	SetHeader("Content-Type", "application/json").
	// 	SetResult(&temp15).
	// 	Get("https://maps.googleapis.com/maps/api/distancematrix/json")

	// data = append(data, temp15)

	// file, _ := json.MarshalIndent(data, "", " ")
	// _ = ioutil.WriteFile("data2.json", file, 0644)
}

func getDestinations(address []string) string {
	destination := ""
	for i, v := range address {
		if len(address)-1 == i {
			destination += v
		} else {
			destination += fmt.Sprintf("%s|", v)
		}
	}
	return destination
}

func filterAddress(address []string, offset int, limit int) []string {
	result := make([]string, 0)
	count := 0
	for i := offset; i < len(address); i++ {
		count++
		result = append(result, address[i])
		if count == limit {
			return result
		}
	}
	return result
}
