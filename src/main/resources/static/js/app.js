document.addEventListener('DOMContentLoaded', async function() {
    const current_date_elem = document.getElementById('current-date');
    const current_body_part_elem = document.getElementById('current-body-part');
    const next_date_elem = document.getElementById('next-date');
    const next_body_part_elem = document.getElementById('next-body-part');

    async function fetchInjectionSchedule() {
        try {
            const response = await fetch('/api/schedules');
            if (!response.ok) {
                throw new Error('Failed to fetch injection schedule data');
            }
            return await response.json();
        } catch (error) {
            console.error('Error fetching injection schedule data:', error);
            return [];
        }
    }

    function renderSchedule(scheduleData) {
        if (scheduleData.length >= 2) {
            current_date_elem.textContent = scheduleData[0].injectionDate;
            current_body_part_elem.textContent = scheduleData[0].bodyPart;
            next_date_elem.textContent = scheduleData[1].injectionDate;
            next_body_part_elem.textContent = scheduleData[1].bodyPart;
        } else if (scheduleData.length === 1) {
            const injection = scheduleData[0];
            current_date_elem.textContent = injection.injectionDate;
            current_body_part_elem.textContent = injection.bodyPart;
            next_date_elem.textContent = injection.injectionDate;
            next_body_part_elem.textContent = injection.bodyPart;
        } else {
            console.error('No injection schedule data found');
        }
    }

    async function markDone() {
        try {
            const scheduleData = await fetchInjectionSchedule();
            if (scheduleData.length === 0) {
                console.error('No injection schedule data found');
                return;
            }
            const currentScheduleId = scheduleData[0].id;
            const response = await fetch(`/api/schedules/${currentScheduleId}/complete`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ id: currentScheduleId })
            });
            if (!response.ok) {
                throw new Error('Failed to mark injection as done');
            }
            await renderSchedule(scheduleData);
        } catch (error) {
            console.error('Error marking injection as done:', error);
        }
    }

    const markDoneBtn = document.getElementById('mark-done-btn');
    if (markDoneBtn) {
        markDoneBtn.addEventListener('click', markDone);
    } else {
        console.error('mark-done-btn not found');
    }

    renderSchedule(await fetchInjectionSchedule());
});
