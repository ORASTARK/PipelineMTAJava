const typeData = {
  add: 2,
  minus: 2,
  times: 2,
  divide: 2,
  sin: 1,
  cos: 1,
  tan: 1,
  cot: 1
}
 
$("#calc_btn").on('click', () => {
  const fc = $("input[type='radio']:checked").val()
  const inputList = $(`.${fc} input`)
  let newUrl = `/${fc}?`
  for (let i = 0; i < typeData[fc]; i ++) {
    newUrl += `p${i + 1}=${$(inputList[i + 1]).val()}&`
  }
  $.ajax({
    url: newUrl.slice(0, -1),
    type: 'GET',
    success: res => {
      $(inputList[typeData[fc] + 1]).val(res.value)
    }
  })
})